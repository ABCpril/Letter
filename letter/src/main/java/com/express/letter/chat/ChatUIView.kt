package com.express.letter.chat

import android.text.TextUtils
import com.angcyo.hyphenate.REMCallBack
import com.angcyo.hyphenate.REMConversation
import com.angcyo.hyphenate.REMMessage
import com.angcyo.hyphenate.REMMessage.addMessageListener
import com.angcyo.hyphenate.REMMessage.removeMessageListener
import com.angcyo.hyphenate.listener.REMMessageListener
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

    init {
        REMMessage.instance().currentChatUserName = username
        REMConversation.markAllMessagesAsRead(username)
    }

    val messageListener = object : REMMessageListener() {
        override fun onNewMessage(messages: MutableList<EMMessage>) {
            super.onNewMessage(messages)
            this@ChatUIView.onNewMessage(messages)
        }
    }

    /**收到新的消息*/
    open fun onNewMessage(messages: MutableList<EMMessage>) {
        val list = (0 until messages.size)
                .map { messages[it] }
                .filter {
                    if (type == EMConversation.EMConversationType.Chat) {
                        TextUtils.equals(it.from, username)
                    } else {
                        TextUtils.equals(it.conversationId(), username)
                    }
                }
        REMConversation.markAllMessagesAsRead(username)
        mExBaseAdapter.appendAllData(list)
        scrollToLastBottom()
    }

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

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        addMessageListener(messageListener)
    }

    override fun onViewUnload() {
        super.onViewUnload()
        removeMessageListener(messageListener)
        REMMessage.instance().currentChatUserName = ""
    }

}