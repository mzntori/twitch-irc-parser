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
    fun extractTag(): TagExtractor {
        return TagExtractor(this)
    }

    fun extractParameter(): ParameterExtractor {
        return ParameterExtractor(this)
    }

    fun extractPrefix(): PrefixExtractor {
        return PrefixExtractor(this)
    }

    fun promote(): IRCMessage {
        return kotlin.runCatching {
            when (command) {
                "001" -> RplWelcomeMessage(this)
                "002" -> RplYourHostMessage(this)
                "003" -> RplCreatedMessage(this)
                "004" -> RplMyInfoMessage(this)
                "353" -> RplNamReplyMessage(this)
                "366" -> RplEndOfNamesMessage(this)
                "372" -> RplMotdMessage(this)
                "375" -> RplMotdStartMessage(this)
                "376" -> RplEnddOfMotdMessage(this)
                "CAP" ->  CapMessage(this)
                "CLEARCHAT" -> ClearChatMessage(this)
                "CLEARMSG" -> ClearMsgMessage(this)
                "JOIN" -> JoinMessage(this)
                "NOTICE" -> NoticeMessage(this)
                "PART" -> PartMessage(this)
                "PING" -> PingMessage(this)
                "PONG" -> PongMessage(this)
                "PRIVMSG" -> PrivMsgMessage(this)
                else -> this
            }
        }.getOrElse { this }
    }

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
            "CAP" ->  CapMessage(this)
            "CLEARCHAT" -> ClearChatMessage(this)
            "CLEARMSG" -> ClearMsgMessage(this)
            "JOIN" -> JoinMessage(this)
            "NOTICE" -> NoticeMessage(this)
            "PART" -> PartMessage(this)
            "PING" -> PingMessage(this)
            "PONG" -> PongMessage(this)
            "PRIVMSG" -> PrivMsgMessage(this)
            else -> this
        }
    }

    override fun toString(): String {
        return "{tags = $tags, prefix = $prefix, command = $command, parameters = $parameters}"
    }
}