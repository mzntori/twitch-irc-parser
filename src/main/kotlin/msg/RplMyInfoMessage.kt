package msg

import extract.ParameterExtractor

class RplMyInfoMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val info: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    val username: String = msg
        .extractParameter()
        .requireIndex(0)
        .asString()
}