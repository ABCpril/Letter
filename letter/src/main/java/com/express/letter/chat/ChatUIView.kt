package com.express.letter.chat

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.angcyo.amap.iview.RAmapUIView
import com.angcyo.hyphenate.REMCallBack
import com.angcyo.hyphenate.REMConversation
import com.angcyo.hyphenate.REMMessage
import com.angcyo.hyphenate.REMMessage.addMessageListener
import com.angcyo.hyphenate.REMMessage.removeMessageListener
import com.angcyo.hyphenate.listener.REMMessageListener
import com.angcyo.uiview.dialog.UIFileSelectorDialog
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.angcyo.uiview.utils.Tip
import com.express.letter.BuildConfig
import com.express.letter.R
import com.express.letter.base.BaseChatUIView
import com.express.letter.chat.emoji.CommandItem
import com.express.letter.chat.holder.BaseChatHolder
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage
import com.lzy.imagepicker.ImageDataSource
import com.lzy.imagepicker.ImagePickerHelper

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

    //添加消息到最后, 并且滚动到底部
    override fun addMessageToLast(dataBea: EMMessage) {
        super.addMessageToLast(dataBea)
        //动态监听消息发送的状态
        dataBea.setMessageStatusCallback(object : REMCallBack() {
            override fun onResult(isError: Boolean, code: Int, message: String?) {
                mExBaseAdapter.notifyItemChanged(dataBea)
            }
        })
    }

    //点击发送消息按钮
    override fun onSendButtonClick() {
        if (inputEditText.isEmpty) {
            return
        }

        val sendMessage = REMMessage.sendMessage(inputEditText.string(), username, type == EMConversation.EMConversationType.GroupChat)
        addMessageToLast(sendMessage)

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

    /**是否是群聊*/
    open fun isGroup() = type == EMConversation.EMConversationType.Chat

    override fun getCommandItems(): List<CommandItem> {
        val items = mutableListOf<CommandItem>()
//        if (BuildConfig.DEBUG) {
        //items.add(CommandItem(R.drawable.ease_chat_takepic_normal, "拍照", View.OnClickListener { }))
        items.add(CommandItem(R.drawable.ease_chat_image_normal, "图片", View.OnClickListener {
            ImagePickerHelper.startImagePicker(mActivity, true, 9, ImageDataSource.IMAGE)
        }))
        items.add(CommandItem(R.drawable.ease_chat_location_normal, "位置", View.OnClickListener {
            //            startIView(AmapUIView {
//                L.e("call: getCommandItems -> $it")
//            })
            startIView(RAmapUIView {
                addMessageToLast(REMMessage.sendLocationMessage(it.latitude, it.longitude, it.address, username, isGroup()))
            })
        }))
        items.add(CommandItem(R.drawable.em_chat_video_normal, "视频", View.OnClickListener {
            ImagePickerHelper.startImagePicker(mActivity, false, 1, ImageDataSource.VIDEO)
        }))
        items.add(CommandItem(R.drawable.em_chat_file_normal, "文件", View.OnClickListener {
            startIView(UIFileSelectorDialog {
                addMessageToLast(REMMessage.sendFileMessage(it.absolutePath, username, isGroup()))
            })
        }))
//        }
        if (type == EMConversation.EMConversationType.Chat) {
            items.add(CommandItem(R.drawable.em_chat_voice_call_normal, "语音电话", View.OnClickListener {
                //startIView(RAmapUIView())
                Tip.tip("暂不支持")
            }))
            items.add(CommandItem(R.drawable.em_chat_video_call_normal, "视频通话", View.OnClickListener {
                Tip.tip("暂不支持")
            }))
        }
        return items
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ImagePickerHelper.getItems(mActivity, requestCode, resultCode, data).map {
            if (it.loadType == ImageDataSource.VIDEO) {
                addMessageToLast(REMMessage.sendVideoMessage(it.path, it.videoThumbPath, (it.videoDuration / 1000).toInt(), username, isGroup()))
            } else {
                addMessageToLast(REMMessage.sendImageMessage(it.path, ImagePickerHelper.isOrigin(requestCode, resultCode, data), username, isGroup()))
            }
        }
    }

}