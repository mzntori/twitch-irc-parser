package data

data class Color(
    val red: UByte,
    val green: UByte,
    val blue: UByte
)

fun String.toColorFromHexOrNull(): Color? {
    val noPrefix = this.removePrefix("#")

    if (noPrefix.length != 6) return null

    val windows = noPrefix.lowercase().windowed(2, 2)

    val bytes: List<UByte> = windows.mapNotNull { it.toUByteOrNull(radix = 16) }

    if (bytes.size != 3) return null

    return Color(
        red = bytes[0],
        green = bytes[1],
        blue = bytes[2],
    )
}
