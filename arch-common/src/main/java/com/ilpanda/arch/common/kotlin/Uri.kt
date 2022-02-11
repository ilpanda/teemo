package com.ilpanda.arch.common.kotlin

import android.net.Uri

val Uri.firstPathSegment: String?
    get() = pathSegments.firstOrNull()