package extract

import exceptions.ConvertionException
import java.time.Instant

class Extraction(private val value: String) {
    /**
     * @return the value as a String like it was extracted.
     */
    fun asString(): String {
        return value
    }

    /**
     * @return a formatted version of the extracted string.
     * Needed for strings with spaces or semicolons in them.
     */
    fun asFormattedStringOrNull(): String? {
        return formatString(value)
    }

    /**
     * @return a formatted version of the extracted string.
     * @throws ConvertionException if string couldn't be formatted.
     */
    fun asFormattedString(): String {
        return formatString(value) ?: throw ConvertionException("Could not format string.")
    }

    /**
     * Splits the extracted String into smaller strings using a delimiter.
     *
     * @param delimiter String to use as delimiter for splitting.
     * @return a List of sub-strings in order.
     */
    fun asStringList(delimiter: String): List<String> {
        return value.split(delimiter)
    }

    /**
     * Converts the extracted string into an [Int].
     *
     * @return the parsed [Int] or `null` if that didn't work.
     */
    fun asIntOrNull(): Int? {
        return value.toIntOrNull()
    }

    /**
     * Converts the extracted string into an [Int].
     *
     * @return the parsed [Int].
     * @throws ConvertionException
     */
    fun asInt(): Int {
        return value.toIntOrNull() ?: throw ConvertionException("Couldn't convert to Int.")
    }

    /**
     * Splits the extracted String into smaller strings using a delimiter.
     * Each String gets parsed into an [Int].
     *
     * @param delimiter String to use as delimiter for splitting.
     * @return a List of [Int] in order.
     * Sub-strings that couldn't be parsed are filtered.
     */
    fun asIntList(delimiter: String): List<Int> {
        return value.split(delimiter).mapNotNull { it.toIntOrNull() }
    }

    /**
     * Converts the extracted string into a [Long].
     *
     * @return the parsed [Long] or `null` if that didn't work.
     */
    fun asLongOrNull(): Long? {
        return value.toLongOrNull()
    }

    /**
     * Converts the extracted string into a [Long].
     *
     * @return the parsed [Long].
     * @throws ConvertionException
     */
    fun asLong(): Long {
        return value.toLongOrNull() ?: throw ConvertionException("Couldn't convert to Long.")
    }

    /**
     * Splits the extracted String into smaller strings using a delimiter.
     * Each String gets parsed into a [Long].
     *
     * @param delimiter String to use as delimiter for splitting.
     * @return a List of [Long] in order.
     * Sub-strings that couldn't be parsed are filtered.
     */
    fun asLongList(delimiter: String): List<Long> {
        return value.split(delimiter).mapNotNull { it.toLongOrNull() }
    }

    /**
     * Converts the extracted string into a [Instant].
     *
     * @return the parsed Instant or `null` if the string couldn't be converted into [Long].
     */
    fun asInstantOrNull(): Instant? {
        return Instant.ofEpochMilli(value.toLongOrNull() ?: return null)
    }

    /**
     * Converts the extracted string into a [Instant].
     *
     * @return the parsed Instant.
     * @throws ConvertionException the string couldn't be converted into [Long]
     */
    fun asInstant(): Instant {
        return Instant.ofEpochMilli(value.toLongOrNull() ?: throw ConvertionException("Couldn't convert to Instant."))
    }

    private fun formatString(raw: String): String? {
        val result = StringBuilder()
        var state = StringFormatState.Text

        for (c in raw) {
            when (state) {
                StringFormatState.Text -> when (c) {
                    '\\' -> state = StringFormatState.Escape
                    '⸝' -> result.append(',')
                    else -> result.append(c)
                }

                StringFormatState.Escape -> {
                    when (c) {
                        '\\' -> result.append('\\')
                        's' -> result.append(' ')
                        ':' -> result.append(';')
                        else -> return null
                    }

                    state = StringFormatState.Text
                }
            }
        }

        return result.toString()
    }

    private enum class StringFormatState {
        Text,
        Escape
    }
}

fun String.toExtraction(): Extraction {
    return Extraction(this)
}