package msg

import extract.ParameterExtractor
import java.time.Instant

class ClearChatMessage(msg: IRCMessage) : IRCMessage(
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

    val channelID: Int = msg
        .extractTag()
        .require("room-id")
        .asInt()

    val timestamp: Instant = msg
        .extractTag()
        .require("tmi-sent-ts")
        .asInstant()

    val targetUsername: String? = msg
        .extractParameter()
        .optional(ParameterExtractor.ParameterType.Text)
        ?.asString()

    val targetID: Int? = msg
        .extractTag()
        .optional("target-user-id")
        ?.asIntOrNull()

    val timeoutDuration: Int? = msg
        .extractTag()
        .optional("ban-duration")
        ?.asIntOrNull()

    val isTimeout: Boolean = timeoutDuration != null
    val isBan: Boolean = !isTimeout && targetID != null
    val isClear: Boolean = targetID == null
}