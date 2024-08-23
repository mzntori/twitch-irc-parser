package msg

import data.*
import extract.ParameterExtractor
import extract.PrefixExtractor
import java.time.Instant

class PrivMsgMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val channelName: String = msg
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

    val bits: Int? = msg
        .extractTag()
        .optional("bits")
        ?.asIntOrNull()

    val userColor: Color? = msg
        .extractTag()
        .optional("color")
        ?.asColorOrNull()

    val clientNonce: String? = msg
        .extractTag()
        .optional("client-nonce")
        ?.asString()

    val formattedClientNonce: String? = msg
        .extractTag()
        .optional("client-nonce")
        ?.asFormattedStringOrNull()

    val senderDisplayName: String = msg
        .extractTag()
        .require("display-name")
        .asString()

    val isEmoteOnly: Boolean = msg
        .extractTag()
        .optional("emote-only")
        ?.asBooleanOrNull() ?: false

    val emotes: List<PositionedEmote> = msg
        .extractTag()
        .optional("emotes")
        ?.asStringList("/")
        ?.map {
            it.toPositionedEmote()
        } ?: emptyList()

    val isFirstTimeChatter: Boolean = msg
        .extractTag()
        .optional("first-msg")
        ?.asBooleanOrNull() ?: false

    // TODO: automod flags

    val messageID: String = msg
        .extractTag()
        .require("id")
        .asString()

    val isMod: Boolean = msg
        .extractTag()
        .require("mod")
        .asBoolean()

    /**
     * This is NOT the opposite of [isFirstTimeChatter], According to [this](https://help.twitch.tv/s/article/chat-highlights?language=en_US#:~:text=Returning%20Chatter%20%2D%20New%20viewers%20who,in%20the%20last%2030%20days) it's triggered for "New viewers who have chatted at least twice in the last 30 days".
     */
    val returningChatter: Boolean = msg
        .extractTag()
        .optional("returning-chatter")
        ?.asBooleanOrNull() ?: false

    val channelID: Int = msg
        .extractTag()
        .require("room-id")
        .asInt()

    val isSubscriber: Boolean = msg
        .extractTag()
        .optional("subscriber")
        ?.asBooleanOrNull() ?: false

    val timestamp: Instant = msg
        .extractTag()
        .require("tmi-sent-ts")
        .asInstant()

    val hasTurbo: Boolean = msg
        .extractTag()
        .optional("turbo")
        ?.asBooleanOrNull() ?: false

    val senderUserID: Int = msg
        .extractTag()
        .require("user-id")
        .asInt()

    val userType: UserType = msg
        .extractTag()
        .optional("user-type")
        ?.asUserType() ?: UserType.Normal

    val isVip: Boolean = msg
        .extractTag()
        .optional("vip")
        ?.exists() ?: false

    val parentMessageID: String? = msg
        .extractTag()
        .optional("reply-parent-msg-id")
        ?.asString()

    val parentMessageUserID: Int? = msg
        .extractTag()
        .optional("reply-parent-user-ud")
        ?.asIntOrNull()

    val parentMessageUsername: String? = msg
        .extractTag()
        .optional("reply-parent-user-login")
        ?.asString()

    val parentMessageDisplayname: String? = msg
        .extractTag()
        .optional("reply-parent-display-name")
        ?.asString()

    val parentMessageText: String? = msg
        .extractTag()
        .optional("reply-parent-msg-body")
        ?.asFormattedStringOrNull()

    val threadRootMessageID: String? = msg
        .extractTag()
        .optional("reply-thread-parent-msg-id")
        ?.asString()

    val threadRootUsername: String? = msg
        .extractTag()
        .optional("reply-thread-parent-user-login")
        ?.asString()
}