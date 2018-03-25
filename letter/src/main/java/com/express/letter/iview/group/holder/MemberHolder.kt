package com.express.letter.iview.group.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.express.letter.chat.ChatUIView
import com.express.letter.iview.BaseItemHolder

/**
 * Created by angcyo on 2018/03/25 12:33
 */
class MemberHolder : BaseItemHolder<String>() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: String) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.tv(R.id.text_view).text = dataBean
        holder.clickItem {
            startIView(ChatUIView(dataBean))
        }
    }
}