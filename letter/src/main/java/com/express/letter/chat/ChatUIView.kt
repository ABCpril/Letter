package com.express.letter.chat

import com.express.letter.base.BaseChatUIView

/**
 * Created by angcyo on 2018/03/24 10:14
 */
open class ChatUIView(val username: String) : BaseChatUIView() {
    override fun getTitleString(): String {
        return username
    }
}