package msg

import extract.ParameterExtractor

class NoticeMessage(msg: IRCMessage) : IRCMessage(
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

    val messageContent: String = msg
        .extractParameter()
        .require(ParameterExtractor.ParameterType.Text)
        .asString()

    val targetUserID: Int? = msg
        .extractTag()
        .optional("target-user-id")
        ?.asIntOrNull()

    val noticeType: NoticeType = msg
        .extractTag()
        .require("msg-id")
        .asNoticeType()

    enum class NoticeType {
        EmoteOnlyOff,
        EmoteOnlyOn,
        FollowersOff,
        FollowersOn,
        FollowersOnZero,
        MsgBanned,
        MsgBadCharacters,
        MsgChannelBlocked,
        MsgChannelSuspended,
        MsgDuplicate,
        MsgEmoteonly,
        MsgFollowersonly,
        MsgFollowersonlyFollowed,
        MsgFollowersonlyZero,
        MsgR9k,
        MsgRatelimit,
        MsgRejected,
        MsgRejectedMandatory,
        MsgRequiresVerifiedPhoneNumber,
        MsgSlowmode,
        MsgSubsonly,
        MsgSuspended,
        MsgTimedout,
        MsgVerifiedEmail,
        SlowOff,
        SlowOn,
        SubsOff,
        SubsOn,
        TosBan,
        UnrecognizedCmd,
        Other
    }
}
