package extract

import exceptions.ExtractionException
import msg.IRCMessage

/**
 * Used for extracting parameters from an [IRCMessage].
 *
 * Can only extract 2 different parameters:
 * - Channel: Extracts the channel the message was sent in.
 * - Text: Extracts the message text in the message.
 * Other parameters should be accessed through the [IRCMessage.tags] field.
 */
class ParameterExtractor(override val msg: IRCMessage) : Extractor<ParameterExtractor.ParameterType> {
    override fun require(key: ParameterType): Extraction {
        return optional(key) ?: throw ExtractionException("Couldn't extract $key")
    }

    override fun optional(key: ParameterType): Extraction? {
        when (key) {
            ParameterType.Text -> msg.parameters.run {
                if (isEmpty() || !last().startsWith(":")) {
                    return null
                } else {
                    return last().substring(1).toExtraction()
                }
            }
            ParameterType.Channel -> return msg.parameters.findLast {
                it.startsWith(":")
            }?.substring(1)?.toExtraction()
        }
    }

    enum class ParameterType {
        Channel,
        Text
    }

    fun requireIndex(idx: Int): Extraction {
        return optionalIndex(idx) ?: throw ExtractionException("$idx not a valid index.")
    }

    fun optionalIndex(idx: Int): Extraction? {
        return msg.parameters.getOrNull(idx)?.toExtraction()
    }
}