package msg

import data.*
import extract.ParameterExtractor
import extract.PrefixExtractor

class WhisperMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val messageText: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    val senderUsername: String = msg
        .extractPrefix()
        .require(PrefixExtractor.PrefixPart.Username)
        .asString()

    val badges: List<Badge> = msg
        .extractTag()
        .optional("badges")
        ?.asStringList(",")
        ?.mapNotNull {
            it.toBadgeOrNull()
        } ?: emptyList()

    val userColor: Color? = msg
        .extractTag()
        .optional("color")
        ?.asColorOrNull()

    val senderDisplayName: String = msg
        .extractTag()
        .optional("display-name")
        ?.asFormattedString() ?: senderUsername

    val emotes: List<PositionedEmote> = msg
        .extractTag()
        .optional("emotes")
        ?.asStringList("/")
        ?.map {
            it.toPositionedEmote()
        } ?: emptyList()

    val messageID: Int = msg
        .extractTag()
        .require("message-id")
        .asInt()

    val threadID: String = msg
        .extractTag()
        .require("thread-id")
        .asString()

    val senderIsTurbo: Boolean = msg
        .extractTag()
        .optional("turbo")
        ?.asBooleanOrNull() ?: false

    val senderID: Int = msg
        .extractTag()
        .require("user-id")
        .asInt()


    val userType: UserType = msg
        .extractTag()
        .optional("user-type")
        ?.asUserType() ?: UserType.Normal
}