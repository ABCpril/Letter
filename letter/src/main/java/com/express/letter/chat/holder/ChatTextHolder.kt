package com.express.letter.chat.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody

/**
 * Created by angcyo on 2018/03/24 11:17
 */
class ChatTextHolder : BaseChatHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EMMessage) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.tv(R.id.message_content_text_view).text = (dataBean.body as EMTextMessageBody).message
    }

    override fun getMessageContentLayoutId(): Int {
        return R.layout.message_text_content_layout
    }
}