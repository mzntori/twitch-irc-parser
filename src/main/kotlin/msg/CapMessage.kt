package msg

import extract.ParameterExtractor

class CapMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    enum class SubCommand {
        Acknowledged,
        NotAcknowledged,
        List,
        Other,
    }

    /**
     * Sub command of CAP.
     * This gives more context to [capabilities].
     */
    val subCommand: SubCommand = when (parameters[1]) {
        "ACK" -> SubCommand.Acknowledged
        "NAK" -> SubCommand.NotAcknowledged
        "LS" -> SubCommand.List
        else -> SubCommand.Other
    }

    /**
     * List of capabilities returned.
     */
    val capabilities: List<String> = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asStringList(" ")
}