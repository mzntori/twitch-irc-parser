package msg

import extract.ParameterExtractor

class PongMessage(msg: IRCMessage) : IRCMessage(
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

    val host: String = msg
        .extractParameter()
        .requireIndex(0)
        .asString()
}