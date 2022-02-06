package cn.ilpanda.arch.extension

import okio.ByteString
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString


fun String.md5(): String = encodeUtf8().md5().hex()

fun String.base64Encode(): String = encodeUtf8().base64()

fun String.base64UrlEncode(): String = encodeUtf8().base64Url()

fun String.sha1(): String = encodeUtf8().sha1().hex()

fun String.sha256(): String = encodeUtf8().sha256().hex()

fun String.sha512(): String = encodeUtf8().sha512().hex()

fun String.hmacSha1Encode(key: String): String = hmacSha1Encode(key.encodeUtf8())

fun String.hmacSha256Encode(key: String): String = hmacSha256Encode(key.encodeUtf8())

fun String.hmacSha512Encode(key: String): String = hmacSha512Encode(key.encodeUtf8())

fun String.hmacSha1Encode(key: ByteString): String = encodeUtf8().hmacSha1(key).hex()

fun String.hmacSha256Encode(key: ByteString): String = encodeUtf8().hmacSha256(key).hex()

fun String.hmacSha512Encode(key: ByteString): String = encodeUtf8().hmacSha512(key).hex()

fun ByteArray.md5(): String = toByteString().md5().hex()

fun ByteArray.base64Encode(): String = toByteString().base64()

fun ByteArray.base64UrlEncode(): String = toByteString().base64Url()

fun ByteArray.sha1(): String = toByteString().sha1().hex()

fun ByteArray.sha256(): String = toByteString().sha256().hex()

fun ByteArray.sha512(): String = toByteString().sha512().hex()

fun ByteArray.hmacSha1Encode(key: String): String = hmacSha1Encode(key.encodeUtf8())

fun ByteArray.hmacSha256Encode(key: String): String = hmacSha256Encode(key.encodeUtf8())

fun ByteArray.hmacSha512Encode(key: String): String = hmacSha512Encode(key.encodeUtf8())

fun ByteArray.hmacSha1Encode(key: ByteString): String = toByteString().hmacSha1(key).hex()

fun ByteArray.hmacSha256Encode(key: ByteString): String = toByteString().hmacSha256(key).hex()

fun ByteArray.hmacSha512Encode(key: ByteString): String = toByteString().hmacSha512(key).hex()
