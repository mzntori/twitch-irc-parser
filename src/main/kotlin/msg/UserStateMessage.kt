package msg

import data.*
import extract.ParameterExtractor
import extract.PrefixExtractor

class UserStateMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val channelUsername: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Channel)
        .asString()

    val senderUsername: String = msg
        .extractPrefix()
        .require(PrefixExtractor.PrefixPart.Username)
        .asString()

    val badgeInfo: List<Badge> = msg
        .extractTag()
        .optional("badge-info")
        ?.asStringList(",")
        ?.mapNotNull {
            it.toBadgeOrNull()
        } ?: emptyList()

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

    val senderDisplayname: String = msg
        .extractTag()
        .optional("display-name")
        ?.asFormattedString() ?: senderUsername

    val emoteSetIDs: List<Int> = msg
        .extractTag()
        .require("emote-sets")
        .asIntList(",")

    val initiatorPrivMsgID: String? = msg
        .extractTag()
        .optional("id")
        ?.asString()

    val isMod: Boolean = msg
        .extractTag()
        .require("mod")
        .asBoolean()

    val isSubscriber: Boolean = msg
        .extractTag()
        .optional("subscriber")
        ?.asBooleanOrNull() ?: false

    val hasTurbo: Boolean = msg
        .extractTag()
        .optional("turbo")
        ?.asBooleanOrNull() ?: false

    val userType: UserType = msg
        .extractTag()
        .requireAllowBlank("user-type")
        .asUserType()
}