package com.express.letter.chat.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.express.letter.iview.call.BaseCallUIView
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMTextMessageBody

/**
 * Created by angcyo on 2018/03/24 11:17
 */
class ChatTextHolder : BaseChatHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EMMessage) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.rtv(R.id.message_content_text_view).apply {
            text = (dataBean.body as EMTextMessageBody).message

            if (dataBean.getBooleanAttribute(BaseCallUIView.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                compoundDrawablePadding = (8 * density()).toInt()
                setLeftIco(R.drawable.ease_chat_video_call_self)
            } else if (dataBean.getBooleanAttribute(BaseCallUIView.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                compoundDrawablePadding = (8 * density()).toInt()
                setLeftIco(R.drawable.ease_chat_voice_call_self)
            } else {
                compoundDrawablePadding = 0
                setLeftIco(-1)
            }
        }
    }

    override fun getMessageContentLayoutId(): Int {
        return R.layout.message_text_content_layout
    }
}