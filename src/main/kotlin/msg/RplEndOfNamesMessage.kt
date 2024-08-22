package msg

import extract.ParameterExtractor

class RplEndOfNamesMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val endOfNamesText: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    val joinedUsername: String = msg
        .extractParameter()
        .requireIndex(0)
        .asString()

    val channelName: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Channel)
        .asString()
}