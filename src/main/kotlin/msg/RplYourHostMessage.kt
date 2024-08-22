package msg

import extract.ParameterExtractor

class RplYourHostMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val yourHostText: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    val host: String? = yourHostText.split(" ").lastOrNull()

    val username: String = msg
        .extractParameter()
        .requireIndex(0)
        .asString()
}