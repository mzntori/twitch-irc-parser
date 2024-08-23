package msg

import extract.ParameterExtractor

class RoomStateMessage(msg: IRCMessage) : IRCMessage(
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

    val channelID: Int? = msg
        .extractTag()
        .optional("room-id")
        ?.asIntOrNull()

    val isEmoteOnly: Boolean? = msg
        .extractTag()
        .optional("emote-only")
        ?.asBooleanOrNull()

    val followersOnlyCooldown: Int? = msg
        .extractTag()
        .optional("followers-only")
        ?.asIntOrNull()

    val isFollowersOnly: Boolean? = followersOnlyCooldown?.run {
        this > -1
    }

    // TODO
}