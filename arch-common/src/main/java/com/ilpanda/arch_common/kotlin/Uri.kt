package com.ilpanda.arch_common.kotlin

import android.net.Uri

val Uri.firstPathSegment: String?
    get() = pathSegments.firstOrNull()