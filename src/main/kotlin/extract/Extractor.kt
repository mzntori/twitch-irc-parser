package extract

import msg.IRCMessage
import exceptions.ExtractionException

interface Extractor<T> {
    val msg: IRCMessage

    /**
     * Used when a value is required to be extracted.
     *
     * @param key key of the item you want to retrieve.
     * @return an [Extraction] of that keys value.
     * If nothing can be extracted or value is empty throws an exception.
     * @throws ExtractionException
     */
    fun require(key: T): Extraction

    /**
     * Used when a value is optional to be extracted.
     *
     * @param key key of the item you want to retrieve.
     * @return an [Extraction] of that keys value.
     * If nothing can be extracted or value is empty returns null.
     */
    fun optional(key: T): Extraction?

    /**
     * Used to check if a given key can be extracted.
     *
     * @param key of the item you want to retrieve.
     * @return whether a value can be extracted with that key.
     */
    fun possible(key: T): Boolean {
        return optional(key) != null
    }
}