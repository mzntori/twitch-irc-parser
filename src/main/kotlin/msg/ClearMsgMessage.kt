package msg

import extract.ParameterExtractor

class ClearMsgMessage(msg: IRCMessage) : IRCMessage(
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

    val targetMessageContent: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    val targetMessageID: String = msg
        .extractTag()
        .require("target-msg-id")
        .asString()

    val targetUsername: String = msg
        .extractTag()
        .require("login")
        .asString()
}