package msg

import extract.ParameterExtractor
import extract.PrefixExtractor

class PartMessage(msg: IRCMessage) : IRCMessage(
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

    val username: String = msg
        .extractPrefix()
        .require(PrefixExtractor.PrefixPart.Username)
        .asString()
}