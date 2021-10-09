package com.mchew.atrestaurants.core

import java.lang.StringBuilder

fun Int?.toPriceString(): String {
    return this?.let {
        val sb = StringBuilder()
        for (i in 0 until this) {
            sb.append("$")
        }
        sb.toString()
    }.orEmpty()
}

fun Int.toRaitingCountString() = "($this)"