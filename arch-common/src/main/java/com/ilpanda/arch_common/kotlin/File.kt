package com.ilpanda.arch_common.kotlin

import okio.HashingSink
import okio.blackholeSink
import okio.buffer
import okio.source
import java.io.File


fun File.md5(): String {
    HashingSink.md5(blackholeSink()).use { hashingSink ->
        this.source().buffer().use { source ->
            source.readAll(hashingSink)
            return hashingSink.hash.hex()
        }
    }
}

fun File.sha1(): String {
    HashingSink.sha1(blackholeSink()).use { hashingSink ->
        this.source().buffer().use { source ->
            source.readAll(hashingSink)
            return hashingSink.hash.hex()
        }
    }
}

fun File.sha256(): String {
    HashingSink.sha256(blackholeSink()).use { hashingSink ->
        this.source().buffer().use { source ->
            source.readAll(hashingSink)
            return hashingSink.hash.hex()
        }
    }
}

fun File.sha512(): String {
    HashingSink.sha256(blackholeSink()).use { hashingSink ->
        this.source().buffer().use { source ->
            source.readAll(hashingSink)
            return hashingSink.hash.hex()
        }
    }
}
