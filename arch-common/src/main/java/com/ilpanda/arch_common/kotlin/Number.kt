package com.ilpanda.arch_common.kotlin

import java.util.*

fun Number.roundTo(
    numFractionDigits: Int
) = "%.${numFractionDigits}f".format(this, Locale.ENGLISH).toDouble()
