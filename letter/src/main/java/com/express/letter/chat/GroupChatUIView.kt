package com.express.letter.chat

import com.angcyo.uiview.model.TitleBarItem
import com.angcyo.uiview.model.TitleBarPattern
import com.express.letter.R
import com.express.letter.iview.group.GroupMembersUIView
import com.hyphenate.chat.EMConversation

/**
 * Created by angcyo on 2018/03/24 10:14
 */
class GroupChatUIView(val groupId: String, val groupName: String) : ChatUIView(groupId, EMConversation.EMConversationType.GroupChat) {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .addRightItem(TitleBarItem.build(R.drawable.ico_info) {
                    startIView(GroupMembersUIView(groupId))
                })
    }

    override fun getTitleString(): String {
        return groupName
    }
}