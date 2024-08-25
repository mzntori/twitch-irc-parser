package extract

import data.Color
import data.UserType
import data.toColorFromHexOrNull
import exceptions.ConvertionException
import msg.NoticeMessage
import msg.UserNoticeMessage
import java.time.Instant

class Extraction(private val value: String) {
    /**
     * Used for converting existence of extraction into a Boolean value.
     *
     * @return always true.
     */
    fun exists(): Boolean {
        return true
    }

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
    fun asFormattedString(): String {
        return formatString(value)
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
     * Converts the extracted string into a [Boolean].
     *
     * @return the parsed [Boolean] or `null` if that didn't work.
     */
    fun asBooleanOrNull(): Boolean? {
        return value.toIntOrNull()?.run { return this != 0 }
    }

    /**
     * Converts the extracted string into a [Boolean].
     *
     * @return the parsed [Boolean].
     * @throws ConvertionException
     */
    fun asBoolean(): Boolean {
        return asBooleanOrNull() ?: throw ConvertionException("Couldn't convert to Boolean.")
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

    /**
     * Converts the extracted string into a [Color].
     *
     * @return the parsed Color or `null` if that didn't work.
     */
    fun asColorOrNull(): Color? {
        return value.toColorFromHexOrNull()
    }

    /**
     * Converts the extracted string into a [Color].
     *
     * @return the parsed Color.
     * @throws ConvertionException
     */
    fun asColor(): Color {
        return value.toColorFromHexOrNull() ?: throw ConvertionException("Couldn't convert to Color.")
    }

    fun asUserType(): UserType {
        return when (value) {
            "" -> UserType.Normal
            "mod" -> UserType.Mod
            "admin" -> UserType.Admin
            "global_mod" -> UserType.GlobalMod
            "staff" -> UserType.Staff
            else -> UserType.Other
        }
    }

    fun asUserNoticeType(): UserNoticeMessage.UserNoticeType {
        return when (value) {
            "sub" -> UserNoticeMessage.UserNoticeType.Sub
            "resub" -> UserNoticeMessage.UserNoticeType.Resub
            "subgift" -> UserNoticeMessage.UserNoticeType.Subgift
            "submysterygift" -> UserNoticeMessage.UserNoticeType.SubMysteryGift
            "giftpaidupgrade" -> UserNoticeMessage.UserNoticeType.GiftPaidUpgrade
            "rewardgift" -> UserNoticeMessage.UserNoticeType.RewardGift
            "anongiftpaidupgrade" -> UserNoticeMessage.UserNoticeType.AnonGiftPaidUpgrade
            "raid" -> UserNoticeMessage.UserNoticeType.Raid
            "unraid" -> UserNoticeMessage.UserNoticeType.Unraid
            "bitsbadgetier" -> UserNoticeMessage.UserNoticeType.BitsBadgeTier
            "standardpayforward" -> UserNoticeMessage.UserNoticeType.StandardPayForward
            "announcement" -> UserNoticeMessage.UserNoticeType.Announcement

            else -> UserNoticeMessage.UserNoticeType.Other
        }
    }

    fun asNoticeType(): NoticeMessage.NoticeType {
        return when (value) {
            "emote_only_off" -> NoticeMessage.NoticeType.EmoteOnlyOff
            "emote_only_on" -> NoticeMessage.NoticeType.EmoteOnlyOn
            "followers_off" -> NoticeMessage.NoticeType.FollowersOff
            "followers_on" -> NoticeMessage.NoticeType.FollowersOn
            "followers_on_zero" -> NoticeMessage.NoticeType.FollowersOnZero
            "msg_banned" -> NoticeMessage.NoticeType.MsgBanned
            "msg_bad_characters" -> NoticeMessage.NoticeType.MsgBadCharacters
            "msg_channel_blocked" -> NoticeMessage.NoticeType.MsgChannelBlocked
            "msg_channel_suspended" -> NoticeMessage.NoticeType.MsgChannelSuspended
            "msg_duplicate" -> NoticeMessage.NoticeType.MsgDuplicate
            "msg_emoteonly" -> NoticeMessage.NoticeType.MsgEmoteonly
            "msg_followersonly" -> NoticeMessage.NoticeType.MsgFollowersonly
            "msg_followersonly_followed" -> NoticeMessage.NoticeType.MsgFollowersonlyFollowed
            "msg_followersonly_zero" -> NoticeMessage.NoticeType.MsgFollowersonlyZero
            "msg_r9k" -> NoticeMessage.NoticeType.MsgR9k
            "msg_ratelimit" -> NoticeMessage.NoticeType.MsgRatelimit
            "msg_rejected" -> NoticeMessage.NoticeType.MsgRejected
            "msg_rejected_mandatory" -> NoticeMessage.NoticeType.MsgRejectedMandatory
            "msg_requires_verified_phone_number" -> NoticeMessage.NoticeType.MsgRequiresVerifiedPhoneNumber
            "msg_slowmode" -> NoticeMessage.NoticeType.MsgSlowmode
            "msg_subsonly" -> NoticeMessage.NoticeType.MsgSubsonly
            "msg_suspended" -> NoticeMessage.NoticeType.MsgSuspended
            "msg_timedout" -> NoticeMessage.NoticeType.MsgTimedout
            "msg_verified_email" -> NoticeMessage.NoticeType.MsgVerifiedEmail
            "slow_off" -> NoticeMessage.NoticeType.SlowOff
            "slow_on" -> NoticeMessage.NoticeType.SlowOn
            "subs_off" -> NoticeMessage.NoticeType.SubsOff
            "subs_on" -> NoticeMessage.NoticeType.SubsOn
            "tos_ban" -> NoticeMessage.NoticeType.TosBan
            "unrecognized_cmd" -> NoticeMessage.NoticeType.UnrecognizedCmd
            else -> NoticeMessage.NoticeType.Other
        }
    }

    private fun formatString(raw: String): String {
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
                        else -> result.append(c)
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