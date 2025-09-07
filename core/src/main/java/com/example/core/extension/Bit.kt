package com.example.core.extension

@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.toIntLittle(index: Int): Int {
    val hex = "%02x".format(this[index])
    return hex.hexToInt()
}

@OptIn(ExperimentalStdlibApi::class)
fun ByteArray.toIntLittle(start: Int, end: Int): Int {
    val hex = copyOfRange(start, end + 1).reversed().joinToString("") {
        "%02x".format(it)
    }
    return hex.hexToInt()
}

fun Int.toBigEndianHexArray(): ByteArray {
    val byteArray = ByteArray(4)
    byteArray[3] = (this and 0xFF).toByte()
    byteArray[2] = (this shr 8 and 0xFF).toByte()
    byteArray[1] = (this shr 16 and 0xFF).toByte()
    byteArray[0] = (this shr 24 and 0xFF).toByte()
    return byteArray
}

fun Int.toInt2ByteLittleArray(): IntArray {
    return intArrayOf(
        (this % 256),
        (this / 256),
    )
}
