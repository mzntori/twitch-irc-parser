package data

data class Prefix(
    val raw: String,
    val username: String?,
    val nickname: String?,
    val hostname: String?,
) {
    enum class ParseState {
        Start,
        Username,
        Nickname,
        Hostname
    }
}

fun String.toPrefix(): Prefix {
    var state: Prefix.ParseState = Prefix.ParseState.Start

    val parts: MutableList<String> =  mutableListOf()
    val buffer: StringBuilder = StringBuilder()

    for (c in this.trim()) {
        when (state) {
            Prefix.ParseState.Start -> when (c) {
                ':' -> {}
                else -> {
                    buffer.append(c)
                    state =  Prefix.ParseState.Username
                }
            }
            Prefix.ParseState.Username -> when (c) {
                '!' -> {
                    parts.add(buffer.toString())
                    buffer.clear()
                    state = Prefix.ParseState.Nickname
                }
                else -> buffer.append(c)
            }
            Prefix.ParseState.Nickname -> when (c) {
                '@' -> {
                    parts.add(buffer.toString())
                    buffer.clear()
                    state =  Prefix.ParseState.Hostname
                }
                else -> buffer.append(c)
            }
            Prefix.ParseState.Hostname -> buffer.append(c)
        }
    }

    if (buffer.isNotBlank()) parts.add(buffer.toString())

    return Prefix(
        raw = this,
        username = if (parts.size == 3) parts[0] else null,
        nickname = if (parts.size == 3) parts[1] else null,
        hostname = parts.lastOrNull(),
    )
}