package com.express.letter.holder

import android.text.TextUtils
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.angcyo.uiview.widget.NoReadNumView
import com.express.letter.R
import com.express.letter.bean.ConversationItem
import com.express.letter.chat.ChatUIView
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMTextMessageBody

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/21 17:12
 * 修改人员：Robi
 * 修改时间：2018/03/21 17:12
 * 修改备注：
 * Version: 1.0.0
 */
class SessionHolder : RExItemHolder<ConversationItem>() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: ConversationItem) {
        val conversation = dataBean.mEMConversation
        val lastMessage = conversation.lastMessage

        //显示的名字
        var username = conversation.conversationId()
        if (conversation.type == EMConversation.EMConversationType.GroupChat) {
            val group = EMClient.getInstance().groupManager().getGroup(username)
            username = if (group != null) group.groupName else username
        } else if (conversation.type == EMConversation.EMConversationType.ChatRoom) {
            val room = EMClient.getInstance().chatroomManager().getChatRoom(username)
            username = if (room != null && !TextUtils.isEmpty(room.name)) room.name else username
        } else {

        }
        holder.tv(R.id.name_view).text = username //lastMessage.from

        //最后一条消息显示
        holder.tv(R.id.tip_view).text = (lastMessage.body as EMTextMessageBody).message

        //消息时间显示
        holder.timeV(R.id.time_view).time = lastMessage.msgTime

        //未读数显示
        val noReadNumView: NoReadNumView = holder.v(R.id.no_read_num_view)
        val msgCount = conversation.unreadMsgCount
        noReadNumView.noReadNum = if (msgCount == 0) -1 else msgCount

        //点击跳转
        holder.clickItem {
            startIView(ChatUIView(username))
        }
    }
}