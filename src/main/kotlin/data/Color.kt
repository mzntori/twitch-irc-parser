package data

data class Color(
    val red: Byte,
    val green: Byte,
    val blue: Byte
)

fun String.toColorFromHexOrNull(): Color? {
    val bytes: List<Byte> = this.removePrefix("#").run {
        if (length != 6) return null
        this
    }.windowed(2, 2).mapNotNull {
        it.toByteOrNull(radix = 16)
    }

    if (bytes.size != 3) return null

    return Color(
        red = bytes[0],
        green = bytes[1],
        blue = bytes[2],
    )
}
