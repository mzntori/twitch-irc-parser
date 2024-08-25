package msg

import data.Prefix
import extract.ParameterExtractor
import extract.PrefixExtractor
import extract.TagExtractor

open class IRCMessage(
    val raw: String,
    val tags: HashMap<String, String>,
    val prefix: Prefix?,
    val command: String,
    val parameters: List<String>
) {
    /**
     * @return [TagExtractor] containing this IRC message.
     */
    fun extractTag(): TagExtractor {
        return TagExtractor(this)
    }

    /**
     * @return [ParameterExtractor] containing this IRC message.
     */
    fun extractParameter(): ParameterExtractor {
        return ParameterExtractor(this)
    }

    /**
     * @return [PrefixExtractor] containing this IRC message.
     */
    fun extractPrefix(): PrefixExtractor {
        return PrefixExtractor(this)
    }

    /**
     * Promotes this IRC message to the commands specific type of IRC message, giving easier accessibility to certain properties of messages.
     *
     * @return The specific type message (extending [IRCMessage]).
     * If promotion fails the object will be returned.
     * If you want to catch the error yourself consider using [promoteThrowing]
     */
    fun promote(): IRCMessage {
        return kotlin.runCatching {
            promoteThrowing()
        }.getOrElse { this }
    }

    /**
     * Promotes this IRC message to the commands specific type of IRC message, giving easier accessibility to certain properties of messages.
     *
     * @return The specific type message (extending [IRCMessage]).
     * The type of message is read from the [command] field and is mapped the following way:
     *
     * - `001` -> [RplWelcomeMessage]
     * - `002` -> [RplYourHostMessage]
     * - `003` -> [RplCreatedMessage]
     * - `004` -> [RplMyInfoMessage]
     * - `353` -> [RplNamReplyMessage]
     * - `366` -> [RplEndOfNamesMessage]
     * - `372` -> [RplMotdMessage]
     * - `375` -> [RplMotdStartMessage]
     * - `376` -> [RplEnddOfMotdMessage]
     * - `CAP` -> [CapMessage]
     * - `CLEARCHAT` -> [ClearChatMessage]
     * - `CLEARMSG` -> [ClearMsgMessage]
     * - `JOIN` -> [JoinMessage]
     * - `NOTICE` -> [NoticeMessage]
     * - `PART` -> [PartMessage]
     * - `PING` -> [PingMessage]
     * - `PONG` -> [PongMessage]
     * - `PRIVMSG` -> [PrivMsgMessage]
     * - `ROOMSTATE` -> [RoomStateMessage]
     * - `USERNOTICE` -> [UserNoticeMessage]
     * - `USERSTATE` -> [UserStateMessage]
     * - `WHISPER` -> [WhisperMessage]
     *
     * @throws ExtractionException message doesn't have all necessary tags/parameters/prefix-parts.
     * @throws ConvertionException failed to convert an extracted value into the required type.
     */
    fun promoteThrowing(): IRCMessage {
        return when (command) {
            "001" -> RplWelcomeMessage(this)
            "002" -> RplYourHostMessage(this)
            "003" -> RplCreatedMessage(this)
            "004" -> RplMyInfoMessage(this)
            "353" -> RplNamReplyMessage(this)
            "366" -> RplEndOfNamesMessage(this)
            "372" -> RplMotdMessage(this)
            "375" -> RplMotdStartMessage(this)
            "376" -> RplEnddOfMotdMessage(this)
            "CAP" -> CapMessage(this)
            "CLEARCHAT" -> ClearChatMessage(this)
            "CLEARMSG" -> ClearMsgMessage(this)
            "JOIN" -> JoinMessage(this)
            "NOTICE" -> NoticeMessage(this)
            "PART" -> PartMessage(this)
            "PING" -> PingMessage(this)
            "PONG" -> PongMessage(this)
            "PRIVMSG" -> PrivMsgMessage(this)
            "ROOMSTATE" -> RoomStateMessage(this)
            "USERNOTICE" -> UserNoticeMessage(this)
            "USERSTATE" -> UserStateMessage(this)
            "WHISPER" -> WhisperMessage(this)
            else -> this
        }
    }

    /**
     * Creates a string showing how things got parse.
     * Might  be useful for debugging
     */
    override fun toString(): String {
        return "{tags = $tags, prefix = $prefix, command = $command, parameters = $parameters}"
    }
}