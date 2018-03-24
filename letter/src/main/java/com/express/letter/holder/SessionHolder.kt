package com.express.letter.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.angcyo.uiview.widget.NoReadNumView
import com.express.letter.R
import com.express.letter.bean.ConversationItem
import com.express.letter.chat.ChatUIView
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
        val lastMessage = dataBean.mEMConversation.lastMessage
        holder.tv(R.id.name_view).text = lastMessage.from
        holder.tv(R.id.tip_view).text = (lastMessage.body as EMTextMessageBody).message

        holder.timeV(R.id.time_view).time = lastMessage.msgTime

        val noReadNumView: NoReadNumView = holder.v(R.id.no_read_num_view)
        val msgCount = dataBean.mEMConversation.unreadMsgCount
        noReadNumView.noReadNum = if (msgCount == 0) -1 else msgCount

        holder.clickItem {
            startIView(ChatUIView(lastMessage.from))
        }
    }
}