package com.express.letter.iview.group.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.express.letter.iview.BaseItemHolder
import com.hyphenate.chat.EMGroupInfo

/**
 * Created by angcyo on 2018/03/25 09:35
 */
class JoinGroupEmptyHolder : BaseItemHolder<EMGroupInfo>() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EMGroupInfo) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.tv(R.id.text_view).text = "暂无可加入的群聊"
    }
}