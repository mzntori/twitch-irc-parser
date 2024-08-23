import data.toPrefix
import msg.IRCMessage

open class TwitchIRCParser() {
    enum class State {
        Start,
        Tag,
        Key,
        Value,
        TagsToCommand,
        Prefix,
        Command,
        ParameterSeperator,
        Parameter,
        Text
    }

    /**
     * Parses a single IRC message from Twitch and returns the result.
     * If the given string has multiple IRC messages seperated by `\r\n` only parses the first one.
     *
     * @param text [String] to parse from.
     * @return message as [IRCMessage].
     * Returns `null` if something goes wrong.
     */
    fun parse(text: String): IRCMessage? {
        if (text.isBlank()) return null

        var state: State = State.Start

        val command = StringBuilder()
        val prefix = StringBuilder()
        val buffer = StringBuilder()

        val tags: HashMap<String, String> = HashMap()
        val parameters: MutableList<String> = mutableListOf()

        var keyBuffer: String = ""

        for (c in text) {
            when (state) {
                State.Start -> when (c) {
                    ' ' -> {}
                    '@' -> state = State.Tag
                    ':' -> {
                        prefix.append(c)
                        state = State.Prefix
                    }

                    '\n', '\r', '\u0000' -> return null
                    else -> {
                        command.append(c)
                        state = State.Command
                    }
                }

                State.Tag -> when (c) {
                    '\n', '\r', '\u0000' -> return null
                    else -> {
                        buffer.append(c)
                        state = State.Key
                    }
                }

                State.Key -> when (c) {
                    ' ' -> state = State.TagsToCommand
                    '=' -> {
                        keyBuffer = buffer.toString()
                        buffer.clear()
                        state = State.Value
                    }

                    '\n', '\r', '\u0000' -> return null
                    else -> buffer.append(c)
                }

                State.Value -> when (c) {
                    ' ', ';' -> {
                        tags[keyBuffer] = buffer.toString()
                        buffer.clear()
                        state = if (c == ';') State.Tag else State.TagsToCommand
                    }

                    '\n', '\r', '\u0000' -> return null
                    else -> buffer.append(c)
                }

                State.TagsToCommand -> when (c) {
                    ' ' -> {}
                    ':' -> {
                        prefix.append(c)
                        state = State.Prefix
                    }

                    '\n', '\r', '\u0000' -> return null
                    else -> {
                        command.append(c)
                        state = State.Command
                    }
                }

                State.Prefix -> when (c) {
                    ' ' -> state = State.TagsToCommand
                    '\n', '\r', '\u0000' -> return null
                    else -> prefix.append(c)
                }

                State.Command -> when (c) {
                    ' ' -> state = State.ParameterSeperator
                    '\n', '\r', '\u0000' -> break
                    else -> command.append(c)
                }

                State.ParameterSeperator -> when (c) {
                    ' ' -> {}
                    '\n', '\r', '\u0000' -> break
                    ':' -> {
                        buffer.append(c)
                        state = State.Text
                    }

                    else -> {
                        buffer.append(c)
                        state = State.Parameter
                    }
                }

                State.Parameter -> when (c) {
                    ' ' -> {
                        parameters.add(buffer.toString())
                        buffer.clear()
                        state = State.ParameterSeperator
                    }

                    '\n', '\r', '\u0000' -> {
                        parameters.add(buffer.toString())
                        break
                    }

                    else -> buffer.append(c)
                }

                State.Text -> buffer.append(c)
            }
        }

        if (state == State.Parameter || state == State.Text) {
            parameters.add(buffer.toString())
        }

        return IRCMessage(
            raw = text,
            tags = tags,
            prefix = if (prefix.isEmpty()) null else prefix.toString().toPrefix(),
            command = command.toString().uppercase(),
            parameters = parameters,
        )
    }

    /**
     * Parses multiple IRC messages from Twitch and returns the results.
     * Each message must be seperated by `\r\n` for parsing to work properly.
     *
     * @param text [String] to parse from.
     * @return messages as a list of [IRCMessage].
     * Messages that couldn't be parsed will not be included in that list.
     */
    fun parseMultiple(text: String): List<IRCMessage> {
        val results: MutableList<IRCMessage?> = mutableListOf()
        val rawMessages: List<String> = text.split("\r\n")

        for (rawMessage in rawMessages) {
            results.add(parse(rawMessage))
        }

        return results.filterNotNull()
    }
}
