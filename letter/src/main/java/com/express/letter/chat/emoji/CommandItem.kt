package com.express.letter.chat.emoji

import android.view.View

/**
 * Created by angcyo on 2018/03/25 19:30
 */
class CommandItem {
    var icoId = -1
    var text = ""
    var onItemClick: View.OnClickListener

    constructor(icoId: Int, text: String, onItemClick: View.OnClickListener) {
        this.icoId = icoId
        this.text = text
        this.onItemClick = onItemClick
    }
}