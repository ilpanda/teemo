package cn.ilpanda.arch.extension

import java.io.File


fun String.isJava(): Boolean = extension.equals("java", ignoreCase = true)

fun String.isKotlinFile(): Boolean = extension.equals("kt", ignoreCase = true)

fun String.isXMLFile(): Boolean = extension.equals("xml", ignoreCase = true)

fun String.isPNGFile(): Boolean = extension.equals("png", ignoreCase = true)

fun String.isJPGFile(): Boolean = extension.equals("jpg", ignoreCase = true)

fun String.isClassFile(): Boolean = extension.equals("class", ignoreCase = true)

fun String.isDexFile(): Boolean = extension.equals("dex", ignoreCase = true)

fun String.isApkFile(): Boolean = extension.equals("apk", ignoreCase = true)

fun String.nameWithoutExtension(): String = substringBeforeLast(".")

fun File.isJava(): Boolean = extension.equals("java", ignoreCase = true)

fun File.isKotlinFile(): Boolean = extension.equals("kt", ignoreCase = true)

fun File.isXMLFile(): Boolean = extension.equals("xml", ignoreCase = true)

fun File.isPNGFile(): Boolean = extension.equals("png", ignoreCase = true)

fun File.isJPGFile(): Boolean = extension.equals("jpg", ignoreCase = true)

fun File.isClassFile(): Boolean = extension.equals("class", ignoreCase = true)

fun File.isDexFile(): Boolean = extension.equals("dex", ignoreCase = true)

fun File.isApkFile(): Boolean = extension.equals("apk", ignoreCase = true)


internal val String.extension: String
    get() = this.substringAfterLast(".", "")

