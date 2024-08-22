package msg

import extract.ParameterExtractor

class PingMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    /**
     * Message the PING command sent.
     * Required to construct PONG command.
     */
    val msgText: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    /**
     * Generates the PONG message the client has to respond with.
     */
    fun generatePong(): String {
        return "PONG :$msgText"
    }
}