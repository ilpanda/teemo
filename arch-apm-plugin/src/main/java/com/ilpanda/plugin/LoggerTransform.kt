package com.ilpanda.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.ilpanda.plugin.extesion.PluginConfigExtension
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.regex.Matcher

@Deprecated("插件 Demo")
class LoggerTransform(pluginConfigExtension: PluginConfigExtension) : Transform() {


    override fun getName(): String {
        return "LoggerTransform"
    }

    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    override fun isIncremental(): Boolean {
        return false
    }

    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)
        _transform(transformInvocation.context, transformInvocation.inputs, transformInvocation.outputProvider, transformInvocation.isIncremental)
    }

    private fun _transform(context: Context, inputs: Collection<TransformInput>, outputProvider: TransformOutputProvider, incremental: Boolean) {
        if (!incremental) {
            outputProvider.deleteAll()
        }

        /**Transform 的 inputs 有两种类型，一种是目录，一种是 jar 包，要分开遍历 */
        inputs.forEach { input: TransformInput ->
            // 遍历目录
            input.directoryInputs.forEach { directoryInput: DirectoryInput ->
                // 当前 transform 的输出目录
                // 如：/Users/hfy/Documents/android/GradlePlugin/app/build/intermediates/transforms/LoggerTransform/debug/54
                val destDir =
                    outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                // 当前 transform 的输入目录
                // 如：/Users/hfy/Documents/android/GradlePlugin/app/build/intermediates/javac/debug/classes
                // 或：/Users/hfy/Documents/android/GradlePlugin/app/build/tmp/kotlin-classes/debug
                val inputDir = directoryInput.file

                inputDir?.also { dir: File ->
                    val modifyMap = hashMapOf<String, File>()
                    // 遍历目录中以 .class 结尾的文件。
                    dir.walkTopDown().filter {
                        it.isFile && it.name.endsWith(".class")
                    }.forEach { classFile: File ->
                        // 是否应该修改 class 文件
                        if (isShouldModify(classFile.name)) {
                            // asm 修改 class 文件
                            val file = modifyClassFile(inputDir, classFile, context.temporaryDir)
                            file?.let {
                                /**key 为包名 + 类名，如：/cn/sensorsdata/autotrack/android/app/MainActivity.class*/
                                val key = classFile.absolutePath.replace(dir.absolutePath, "")
                                modifyMap.put(key, it)
                            }
                        }
                    }

                    // 将当前 transform 的输入目录复制到输出目录
                    FileUtils.copyDirectory(inputDir, destDir)

                    // 将修改完的 class 文件复制到 destDir
                    modifyMap.forEach { key, classFile ->
                        // target: asm 修改 class 文件的路径
                        val target = File(destDir, key)
                        // 将 target 复制到对应的目录，overwrite 为 true，表示删除原 class 文件。
                        classFile.copyTo(target, overwrite = true)
                        // classFile 为临时文件，删除临时文件。
                        classFile.delete()
                    }
                }
            }

            // 遍历 jar
            input.jarInputs.forEach { jarInput: JarInput ->
                // jarInput: /Users/hfy/.gradle/caches/transforms-3/948be3c4b77312fd5ffebcf4699dca2a/transformed/jetified-kotlin-stdlib-jdk8-1.5.20.jar
                // 或：/Users/hfy/.gradle/caches/transforms-3/e0b1c78d8edf2fe84c3a646e8cc5ca84/transformed/jetified-core-ktx-1.7.0-runtime.jar
                // 或：/Users/hfy/.gradle/caches/transforms-3/057f15fdf00b982ad605c4194e9a7340/transformed/material-1.5.0-runtime.jar
                var inputJarName = jarInput.file.name

                // 截取文件路径的 md5 值重命名输出文件,因为可能同名,会覆盖
                val hexName = DigestUtils.md5Hex(jarInput.file.absolutePath).substring(0, 8)

                // 获取 jar 名字
                if (inputJarName.endsWith(".jar")) {
                    inputJarName = inputJarName.substring(0, inputJarName.length - 4)
                }

                // 获得输出文件
                // 如：/Users/hfy/Documents/android/GradlePlugin/app/build/intermediates/transforms/LoggerTransform/debug/0.jar
                val dest = outputProvider.getContentLocation(inputJarName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)

                // 获取修改 class 文件后的 jar 包
                // context.temporaryDir : /Users/hfy/Documents/android/GradlePlugin/app/build/tmp/transformClassesWithLoggerTransformForDebug
                // inputFile 如 /Users/hfy/Documents/android/GradlePlugin/app/build/tmp/transformClassesWithLoggerTransformForDebug/3b81849ejetified-kotlin-stdlib-jdk8-1.5.20.jar
                var inputFile = modifyJar(jarInput.file, context.temporaryDir, true)

                // 将 inputFile 复制到 dest
                inputFile.copyTo(dest, overwrite = true)
            }
        }
    }

    private val excludeNameSet = hashSetOf("android.support", "androidx")

    /**
     * asm 是否应该修改 class 文件。
     * @param className class 文件名称。
     */
    private fun isShouldModify(className: String): Boolean {
        excludeNameSet.forEach {
            if (it.startsWith(className)) {
                return false
            }
        }

        if (className.contains("R$") ||
            className.contains("R2$") ||
            className.contains("R.class") ||
            className.contains("R2.class") ||
            className.contains("BuildConfig.class")
        ) {
            return false
        }
        return true
    }

    private fun modifyClassFile(dir: File, classFile: File, temporaryDir: File): File? {
        val className = path2ClassName(classFile.absolutePath.replace(dir.absolutePath + File.separator, ""))

        val sourceClassBytes = classFile.readBytes()
        val modifiedClassBytes = modifyClass(sourceClassBytes)

        modifiedClassBytes?.let {
            val modifiedFile = File(temporaryDir, className.replace(".", "") + ".class")
            if (modifiedFile.exists()) {
                modifiedFile.delete()
            }
            modifiedFile.createNewFile()
            FileOutputStream(modifiedFile).write(modifiedClassBytes)
            return modifiedFile
        }

        return null
    }

    private fun path2ClassName(pathName: String): String {
        return pathName.replace(File.separator, ".").replace(".class", "")
    }

    /**
     * 使用 asm 修改 class 文件
     */
    private fun modifyClass(srcClass: ByteArray): ByteArray? {
//        val classWriter = ClassWriter(ClassWriter.COMPUTE_MAXS)
//        val classVisitor = SensorsAnalyticsClassVisitor(classWriter)
//        val cr = ClassReader(srcClass)
//        cr.accept(classVisitor, ClassReader.SKIP_FRAMES)
//        return classWriter.toByteArray()
        return srcClass
    }


    /**
     * 读取并修改 jar 包中的 class 文件
     * @param jarFile 源文件
     * @param tempDir 存放修改后 jarFile 的临时目录
     * @param nameHex 对修改后 jarFile 的名称进行 hex
     */
    private fun modifyJar(jarFile: File, tempDir: File, nameHex: Boolean): File {

        val file = JarFile(jarFile, false)

        // 设置输出到的 jar 名称
        var hexName = ""
        if (nameHex) {
            hexName = DigestUtils.md5Hex(jarFile.absolutePath).substring(0, 8)
        }

        val outputJar = File(tempDir, hexName + jarFile.name)

        val jarOutputStream = JarOutputStream(FileOutputStream(outputJar))

        val entries = file.entries()

        while (entries.hasMoreElements()) {
            val jarEntry: JarEntry = entries.nextElement()

            val inputStream = file.getInputStream(jarEntry)

            val entryName = jarEntry.name

            if (entryName.endsWith(".DSA") || entryName.endsWith(".SF")) {
                //ignore
            } else {
                var className: String = ""
                val newJarEntry = JarEntry(entryName)
                jarOutputStream.putNextEntry(newJarEntry)
                var modifiedClassBytes: ByteArray? = null
                val sourceClassBytes = inputStream.readBytes()
                if (entryName.endsWith(".class")) {
                    className = entryName.replace(Matcher.quoteReplacement(File.separator), ".").replace(".class", "")
                    // 是否应该修改 class
                    if (isShouldModify(className)) {
                        // 使用 asm 修改 class
                        modifiedClassBytes = modifyClass(sourceClassBytes)
                    }
                }

                if (modifiedClassBytes == null) {
                    modifiedClassBytes = sourceClassBytes
                }

                jarOutputStream.write(modifiedClassBytes)
                jarOutputStream.closeEntry()
            }
        }

        jarOutputStream.close()
        file.close()
        return outputJar
    }

}