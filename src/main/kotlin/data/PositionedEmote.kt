package data

data class PositionedEmote(
    val id: String,
    val positions: List<IntRange>
)

private enum class States {
    ID,
    Start,
    End
}

private var state = States.ID
private val idBuffer = StringBuilder()
private val startBuffer = StringBuilder()
private val endBuffer = StringBuilder()
private val rangeStrings: MutableList<Pair<String, String>> = mutableListOf()

fun String.toPositionedEmote(): PositionedEmote {
    state = States.ID
    idBuffer.clear()
    startBuffer.clear()
    endBuffer.clear()
    rangeStrings.clear()

    for (c in this) {
        when (state) {
            States.ID -> when (c) {
                ':' -> state = States.Start
                else -> idBuffer.append(c)
            }

            States.Start -> when (c) {
                '-' -> state = States.End
                in '0'..'9' -> startBuffer.append(c)
                else -> {}
            }

            States.End -> when (c) {
                ',' -> {
                    rangeStrings.add(Pair(startBuffer.toString(), endBuffer.toString()))
                    startBuffer.clear()
                    endBuffer.clear()
                    state = States.Start
                }
                in '0'..'9' -> endBuffer.append(c)
                else -> {}
            }
        }
    }

    if (state ==  States.End) rangeStrings.add(Pair(startBuffer.toString(), endBuffer.toString()))

    return PositionedEmote(
        id = idBuffer.toString(),
        positions = rangeStrings.mapNotNull {
            val from: Int = it.first.toIntOrNull() ?: return@mapNotNull null
            val to: Int = it.second.toIntOrNull() ?: return@mapNotNull null

            from..to
        },
    )
}