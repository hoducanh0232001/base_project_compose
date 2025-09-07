package com.example.core.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

val Int.withComma
    get() = String.format(Locale.US, "%,d", this)

val Long.withComma
    get() = String.format(Locale.US, "%,d", this)

val Double.withComma
    get() = try {
        DecimalFormat("###,##0.00", DecimalFormatSymbols.getInstance(Locale.US)).format(this).replace("\\.00$".toRegex(), "")
    } catch (e: Throwable) {
        ""
    }

fun Double.toComma(depth: Int): String {
    return try {
        val numberZero = StringBuilder()
        repeat(depth) { numberZero.append("0") }
        DecimalFormat("###,##0.$numberZero", DecimalFormatSymbols.getInstance(Locale.US)).format(this)
            .replace("(\\.\\d*)0+$".toRegex(), "$1")
            .replace("\\.0+$".toRegex(), "")
    } catch (e: Throwable) {
        ""
    }
}

fun Float.toFixed(depth: Int): String = toDouble().toFixed(depth)

fun Double.toFixed(depth: Int): String {
    return String.format(Locale.US, "%.${depth}f", this)
        .replace("(\\.\\d*)0+$".toRegex(), "$1")
        .replace("\\.0+$".toRegex(), "")
}
