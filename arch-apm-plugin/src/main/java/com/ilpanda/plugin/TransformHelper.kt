package com.ilpanda.plugin


import com.android.build.api.transform.*
import com.ilpanda.plugin.cv.InterceptorChain
import com.ilpanda.plugin.extesion.PluginConfigExtension
import com.ilpanda.plugin.utils.PluginLog
import org.apache.commons.io.FileUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import java.io.File
import java.io.FileOutputStream
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

internal object TransformHelper {
    // 配置信息
    lateinit var plugConfigExtension: PluginConfigExtension

    /**
     * 遍历处理 Jar
     */
    fun transformJars(jarInput: JarInput, outputProvider: TransformOutputProvider, isIncremental: Boolean) {
        val jarName = jarInput.name
        val status = jarInput.status
        val destFile = outputProvider.getContentLocation(jarName, jarInput.contentTypes, jarInput.scopes, Format.JAR)
        PluginLog.info("TransformHelper[transformJars], jar = $jarName, status = $status, isIncremental = $isIncremental")
        if (isIncremental) {
            when (status) {
                Status.ADDED -> {
                    handleJarFile(jarInput, destFile)
                }
                Status.CHANGED -> {
                    handleJarFile(jarInput, destFile)
                }
                Status.REMOVED -> {
                    if (destFile.exists()) {
                        destFile.delete()
                    }
                }
                Status.NOTCHANGED -> {

                }
                else -> {

                }
            }
        } else {
            handleJarFile(jarInput, destFile)
        }
    }

    fun transformDirectory(directoryInput: DirectoryInput, outputProvider: TransformOutputProvider, isIncremental: Boolean) {
        val sourceFileDir = directoryInput.file
        val name = sourceFileDir.name
        val destDir = outputProvider.getContentLocation(name, directoryInput.contentTypes,
            directoryInput.scopes, Format.DIRECTORY)
        if (!destDir.exists()) {
            destDir.mkdirs()
        }
        PluginLog.info("TransformHelper[transformDirectory], name = $name, sourceFile Path = ${sourceFileDir.absolutePath}, destFile Path = ${destDir.absolutePath}, isIncremental = $isIncremental")
        if (isIncremental) {
            val changeFiles = directoryInput.changedFiles
            for (changeFile in changeFiles) {
                val status = changeFile.value
                val inputFile = changeFile.key
                val destPath = inputFile.absolutePath.replace(sourceFileDir.absolutePath, destDir.absolutePath)
                val destFile = File(destPath)
                PluginLog.info("目录：$destPath，状态：$status")
                when (status) {
                    Status.NOTCHANGED -> {

                    }
                    Status.REMOVED -> {
                        if (destFile.exists()) {
                            destFile.delete()
                        }
                    }
                    Status.CHANGED, Status.ADDED -> {
                        handleSingeFile(inputFile, destFile, destDir.absolutePath)
                    }
                    else -> {
                    }
                }
            }
        } else {
            handleDirectory(sourceFileDir, destDir, destDir.absolutePath)
        }
    }

    private fun handleJarFile(jarInput: JarInput, destFile: File) {
        // 空的 jar 包不进行处理
        if (jarInput.file == null || jarInput.file.length() == 0L) {
            PluginLog.info("handleJarFile, ${jarInput.file.absolutePath} is null")
            return
        }
        // 构建 JarFile 文件
        val modifyJar = JarFile(jarInput.file, false)
        // 创建目标文件流
        val jarOutputStream = JarOutputStream(FileOutputStream(destFile))
        val enumerations = modifyJar.entries()
        // 遍历 Jar 文件进行处理
        for (jarEntry in enumerations) {
            val inputStream = modifyJar.getInputStream(jarEntry)
            val entryName = jarEntry.name
            val tempEntry = JarEntry(entryName)
            jarOutputStream.putNextEntry(tempEntry)
            var modifyClassBytes: ByteArray? = null
            val destClassBytes = inputStream.readBytes()

            if (!jarEntry.isDirectory && supportModify(entryName.replace("/", "."))) {
                modifyClassBytes = modifyClass(destClassBytes)
            }

            if (modifyClassBytes != null) {
                jarOutputStream.write(modifyClassBytes)
            } else {
                jarOutputStream.write(destClassBytes)
            }
            jarOutputStream.flush()
            jarOutputStream.closeEntry()
        }
        jarOutputStream.close()
        modifyJar.close()
    }

    private fun handleDirectory(sourceFileDir: File, destFileDir: File, destBaseDir: String) {
        if (sourceFileDir.isDirectory) {
            com.android.utils.FileUtils.getAllFiles(sourceFileDir).forEach {
                val inputFile: String = it.absolutePath
                val outputFile = File(inputFile.replace(sourceFileDir.absolutePath, destFileDir.absolutePath))
                handleSingeFile(it, outputFile, destBaseDir)
            }
        }
    }

    private fun handleSingeFile(inputFile: File, destFile: File, destBaseDir: String) {
        val sourceBytes = inputFile.readBytes()
        var modifyBytes: ByteArray? = null
        if (supportModify(destFile.absolutePath.replace(destBaseDir + File.separator, "").replace(File.separator, "."))) {
            modifyBytes = modifyClass(sourceBytes)
        }
        if (modifyBytes != null) {
            FileUtils.touch(destFile)
            destFile.writeBytes(modifyBytes)
        } else {
            if (inputFile.isFile) {
                FileUtils.touch(destFile)
                FileUtils.copyFile(inputFile, destFile)
            }
        }
    }

    private fun modifyClass(sourceBytes: ByteArray): ByteArray? {
        try {
            val classReader = ClassReader(sourceBytes)
            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
            val classVisitor = InterceptorChain(classWriter, plugConfigExtension)
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
            return classWriter.toByteArray()
        } catch (exception: Exception) {
            PluginLog.error("modify class exception = ${exception.printStackTrace()}")
        }
        return null
    }

    /**
     * 编译时生成的 BuildConfig.class、R$xxx 等文件不参与 transform
     * @param fullClassName 类名
     */
    private fun supportModify(fullClassName: String): Boolean {
        return fullClassName.endsWith(".class")
                && !fullClassName.contains("R$")
                && !fullClassName.contains("R.class")
                && !fullClassName.contains("BuildConfig.class")
    }


}
