package com.express.letter.chat

import android.text.TextUtils
import com.angcyo.hyphenate.REMCallBack
import com.angcyo.hyphenate.REMMessage
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.express.letter.BuildConfig
import com.express.letter.base.BaseChatUIView
import com.express.letter.chat.holder.BaseChatHolder
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage

/**
 * Created by angcyo on 2018/03/24 10:14
 */
open class ChatUIView(val username: String,
                      val type: EMConversation.EMConversationType = EMConversation.EMConversationType.Chat)
    : BaseChatUIView() {
    override fun getTitleString(): String {
        return username
    }

    override fun onCreateItemHolder(itemHolder: RExItemHolder<EMMessage>) {
        super.onCreateItemHolder(itemHolder)
        if (itemHolder is BaseChatHolder) {
            itemHolder.chatUIView = this
        }
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        val allMessages = REMMessage.getAllMessages(username, type)
        onUILoadFinish(allMessages)
        if (TextUtils.equals("onViewShowFirst", extend)) {
            scrollToLastBottom()
        }
    }

    override fun onSendButtonClick() {
        val sendMessage = REMMessage.sendMessage(inputEditText.string(), username, type == EMConversation.EMConversationType.GroupChat)
        addMessageToLast(sendMessage)

        sendMessage.setMessageStatusCallback(object : REMCallBack() {
            override fun onResult(isError: Boolean, code: Int, message: String?) {
                mExBaseAdapter.notifyItemChanged(sendMessage)
            }
        })

        if (BuildConfig.DEBUG) {
        } else {
            super.onSendButtonClick()
        }
    }
}