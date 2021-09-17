package com.ilpanda.arch_common.kotlin

import okio.ByteString
import okio.ByteString.Companion.encodeUtf8


fun String?.valid(): Boolean = this != null && !this.equals("null", true) && this.trim().isNotEmpty()

fun String?.md5(): String? = this?.encodeUtf8()?.md5()?.hex()

fun String?.base64(): String? = this?.encodeUtf8()?.base64()

fun String?.base64Url(): String? = this?.encodeUtf8()?.base64Url()

fun String?.sha1(): String? = this?.encodeUtf8()?.sha1()?.hex()

fun String?.sha256(): String? = this?.encodeUtf8()?.sha256()?.hex()

fun String?.sha512(): String? = this?.encodeUtf8()?.sha512()?.hex()

fun String?.hmacSha1(key: String): String? = this.hmacSha1(key.encodeUtf8())

fun String?.hmacSha256(key: String): String? = this.hmacSha256(key.encodeUtf8())

fun String?.hmacSha512(key: String): String? = this.hmacSha512(key.encodeUtf8())

fun String?.hmacSha1(key: ByteString): String? = this?.encodeUtf8()?.hmacSha1(key)?.hex()

fun String?.hmacSha256(key: ByteString): String? = this?.encodeUtf8()?.hmacSha256(key)?.hex()

fun String?.hmacSha512(key: ByteString): String? = this?.encodeUtf8()?.hmacSha512(key)?.hex()
