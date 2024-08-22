package extract

import exceptions.ExtractionException
import msg.IRCMessage

/**
 * Used for extracting tags from an [IRCMessage].
 */
class TagExtractor(override val msg: IRCMessage) : Extractor<String> {
    override fun require(key: String): Extraction {
        return optional(key) ?: throw ExtractionException("Key $key not found.")
    }

    override fun optional(key: String): Extraction? {
        return msg.tags[key]?.ifBlank { null }?.toExtraction()
    }
}