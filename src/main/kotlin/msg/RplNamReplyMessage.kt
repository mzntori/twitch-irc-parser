package msg

import extract.ParameterExtractor

class RplNamReplyMessage(msg: IRCMessage) : IRCMessage(
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

    val joinedUsername: String = msg
        .extractParameter()
        .requireIndex(0)
        .asString()

    val channelName: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Channel)
        .asString()

    val joinedUsers: List<String> = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asStringList(" ")
}