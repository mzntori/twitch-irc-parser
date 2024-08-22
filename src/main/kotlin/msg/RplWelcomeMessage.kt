package msg

import extract.ParameterExtractor

class RplWelcomeMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val welcomeText: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    val username: String = msg
        .extractParameter()
        .requireIndex(0)
        .asString()
}