package extract

import exceptions.ExtractionException
import msg.IRCMessage

class PrefixExtractor(override val msg: IRCMessage) : Extractor<PrefixExtractor.PrefixPart> {
    enum class PrefixPart {
        Username,
        Nickname,
        Hostname,
    }

    override fun require(key: PrefixPart): Extraction {
        return optional(key) ?: throw ExtractionException("Could not extract $key")
    }

    override fun optional(key: PrefixPart): Extraction? {
        return when (key) {
            PrefixPart.Username -> msg.prefix?.username?.toExtraction()
            PrefixPart.Nickname -> msg.prefix?.nickname?.toExtraction()
            PrefixPart.Hostname -> msg.prefix?.hostname?.toExtraction()
        }
    }
}