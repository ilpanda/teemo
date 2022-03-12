package cn.ilpanda.arch.extension

import okio.*
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString
import java.io.File
import kotlin.io.use

@Throws(IOException::class)
fun File.copy(dst: File) {
    source().buffer().use { source ->
        dst.sink().buffer().use {
            it.writeAll(source)
        }
    }
}

fun File.checkMD5(md5: String): Boolean = md5().equals(md5, true)

fun File.checkSHA1(sha1: String): Boolean = sha1().equals(sha1, true)

fun File.checkSHA256(sha256: String): Boolean = sha256().equals(sha256, true)

fun File.checkSHA512(sha512: String): Boolean = sha512().equals(sha512, true)

fun File.md5(): String = hash(HashingSink.md5(blackholeSink()))

fun File.sha1(): String = hash(HashingSink.sha1(blackholeSink()))

fun File.sha256(): String = hash(HashingSink.sha256(blackholeSink()))

fun File.sha512(): String = hash(HashingSink.sha512(blackholeSink()))

fun File.hmacSha1(key: ByteArray): String = hmacSha1(key.toByteString())

fun File.hmacSha1(key: String): String = hmacSha1(key.encodeUtf8())

fun File.hmacSha1(key: ByteString): String = hash(HashingSink.hmacSha1(blackholeSink(), key))

fun File.hmacSha256(key: ByteArray): String = hmacSha256(key.toByteString())

fun File.hmacSha256(key: String): String = hmacSha256(key.encodeUtf8())

fun File.hmacSha256(key: ByteString): String = hash(HashingSink.hmacSha256(blackholeSink(), key))

fun File.hmacSha512(key: ByteArray): String = hmacSha512(key.toByteString())

fun File.hmacSha512(key: String): String = hmacSha512(key.encodeUtf8())

fun File.hmacSha512(key: ByteString): String = hash(HashingSink.hmacSha512(blackholeSink(), key))

private fun File.hash(hashingSink: HashingSink) =
    hashingSink.use { it ->
        this.source().buffer().use { source ->
            source.readAll(it)
            it.hash.hex()
        }
    }

/**
 * @return 获取指定路径下所有文件，包括子目录。
 */
fun File.allFiles(): Sequence<File> {
    return this.walkTopDown().filter {
        it.isFile
    }
}

/**
 * Implements the same behaviour as the "touch" utility on Unix. It creates
 * a new file with size 0 or, if the file exists already, it is opened and
 * closed without modifying it, but updating the file date and time.
 * <p>
 */
@Throws(IOException::class)
fun File.touch() {
    if (!exists()) {
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory) {
                throw  IOException("Directory '$parent' could not be created");
            }
        }
    }
    outputStream().use { }

    val success: Boolean = setLastModified(System.currentTimeMillis())

    if (!success) {
        throw  IOException("Unable to set the last modification time for $this");
    }
}







