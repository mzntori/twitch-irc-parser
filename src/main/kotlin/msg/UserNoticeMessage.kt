package msg

import extract.ParameterExtractor

class UserNoticeMessage(msg: IRCMessage) : IRCMessage(
    raw = msg.raw,
    tags = msg.tags,
    prefix = msg.prefix,
    command = msg.command,
    parameters = msg.parameters
) {
    val userMessageText: String? = msg
        .extractParameter()
        .optional(ParameterExtractor.ParameterType.Text)
        ?.asString()

    val channelName: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Channel)
        .asString()

    val noticeType: UserNoticeType =  msg
        .extractTag()
        .require("msg-id")
        .asUserNoticeType()

    val systemMessage: String = msg
        .extractTag()
        .require("system-msg")
        .asFormattedString()

    enum class UserNoticeType {
        Sub,
        Resub,
        Subgift,
        SubMysteryGift,
        GiftPaidUpgrade,
        RewardGift,
        AnonGiftPaidUpgrade,
        Raid,
        Unraid,
        BitsBadgeTier,
        Announcement,
        StandardPayForward,
        Other
    }
}