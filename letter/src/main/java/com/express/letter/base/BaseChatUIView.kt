package com.express.letter.base

import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.angcyo.hyphenate.REMMessage
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.iview.UIChatIView
import com.angcyo.uiview.kotlin.onEmptyText
import com.angcyo.uiview.recycler.RRecyclerView
import com.angcyo.uiview.recycler.adapter.RExItem
import com.angcyo.uiview.widget.Button
import com.angcyo.uiview.widget.ExEditText
import com.express.letter.R
import com.express.letter.chat.emoji.CommandAdapter
import com.express.letter.chat.emoji.CommandItem
import com.express.letter.chat.emoji.EmojiAdapter
import com.express.letter.chat.emoji.EmojiTextView
import com.express.letter.chat.holder.BaseChatHolder
import com.express.letter.chat.holder.ChatTextHolder
import com.hyphenate.chat.EMMessage

/**
 * 聊天界面, 基类
 * Created by angcyo on 2018/03/24 10:02
 */
open class BaseChatUIView : UIChatIView<String, EMMessage>() {
    lateinit var inputEditText: ExEditText
    lateinit var addButton: ImageView
    lateinit var emojiButton: ImageView
    lateinit var sendButton: Button
    lateinit var emojiRecyclerView: RRecyclerView

    companion object {
        //表情
        const val EMOJI_TYPE_EMOJI = 1
        //功能
        const val EMOJI_TYPE_COMMAND = 2
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, EMMessage>>) {
        allRegItems.add(RExItem(REMMessage.M_TYPE_CMD, R.layout.base_chat_item_layout, BaseChatHolder::class.java))
        allRegItems.add(RExItem(REMMessage.M_TYPE_TXT, R.layout.base_chat_item_layout, ChatTextHolder::class.java))
    }

    override fun getItemTypeFromData(data: EMMessage): String {
        return REMMessage.getEMessageType(data)
    }

    private fun clearButtonStatue() {
        addButton.setImageResource(R.drawable.ease_type_select_btn_nor)
        emojiButton.setImageResource(R.drawable.ease_chatting_biaoqing_btn_normal)
    }

    override fun onFastScrollToTop(recyclerView: RRecyclerView) {
        super.onFastScrollToTop(recyclerView)
        if (recyclerView.isLastItemVisible(true)) {
            showSoftInput(inputEditText)
        }
    }

    override fun afterInflateView(baseContentLayout: ContentLayout) {
        super.afterInflateView(baseContentLayout)
        inputEditText.onEmptyText {
            if (it) {
                mViewHolder.gone(sendButton)
            } else {
                mViewHolder.visible(sendButton)
            }
        }
    }

    override fun initEmojiLayout(chatEmojiRootFrameLayout: FrameLayout, inflater: LayoutInflater) {
        super.initEmojiLayout(chatEmojiRootFrameLayout, inflater)
        inflater.inflate(R.layout.base_chat_emoji_layout, chatEmojiRootFrameLayout)
        emojiRecyclerView = chatEmojiRootFrameLayout.findViewById(R.id.emoji_recycler_view)
    }

    open fun changeEmojiLayoutTo(type: Int) {
        when (type) {
            EMOJI_TYPE_EMOJI -> {
                emojiRecyclerView.tag = "GV8"
                emojiRecyclerView.adapter = EmojiAdapter(mActivity).apply {
                    onEmojiClick = {
                        inputEditText.insert(it.emojiText)
                        EmojiTextView.showEmoji(inputEditText)
                    }
                }
            }
            EMOJI_TYPE_COMMAND -> {
                emojiRecyclerView.tag = "GV4"
                emojiRecyclerView.adapter = CommandAdapter(mActivity, getCommandItems())
            }
        }
    }

    open fun getCommandItems(): List<CommandItem> {
        val items = mutableListOf<CommandItem>()
        return items
    }

    override fun onEmojiLayoutChange(isEmojiShow: Boolean, isKeyboardShow: Boolean, height: Int) {
        super.onEmojiLayoutChange(isEmojiShow, isKeyboardShow, height)
        if (isKeyboardShow || !isEmojiShow) {
            clearButtonStatue()
        }
    }

    override fun initInputLayout(chatInputControlFrameLayout: FrameLayout, inflater: LayoutInflater) {
        super.initInputLayout(chatInputControlFrameLayout, inflater)
        inflater.inflate(R.layout.base_chat_input_layout, chatInputControlFrameLayout)
        inputEditText = chatInputControlFrameLayout.findViewById(R.id.edit_text_view)
        addButton = chatInputControlFrameLayout.findViewById(R.id.add_button)
        emojiButton = chatInputControlFrameLayout.findViewById(R.id.emoji_button)
        sendButton = chatInputControlFrameLayout.findViewById(R.id.send_button)

        click(addButton) {
            clearButtonStatue()
            addButton.setImageResource(R.drawable.ease_type_select_btn_pressed)
            changeEmojiLayoutTo(EMOJI_TYPE_COMMAND)
            softInputLayout.showEmojiLayout()
        }
        click(emojiButton) {
            clearButtonStatue()
            emojiButton.setImageResource(R.drawable.ease_chatting_biaoqing_btn_enable)
            changeEmojiLayoutTo(EMOJI_TYPE_EMOJI)
            softInputLayout.showEmojiLayout()
        }
        click(sendButton) {
            onSendButtonClick()
        }
    }

    open fun onSendButtonClick() {
        inputEditText.setInputText("")
    }
}