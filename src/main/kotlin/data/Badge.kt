package data

data class Badge(
    val name: String,
    val version: Int
)

fun String.toBadgeOrNull(): Badge? {
    val split = this.split("/")

    if (split.size != 2) return null

    return Badge(
        name = split[0],
        version = split[1].toIntOrNull() ?: return null
    )
}
