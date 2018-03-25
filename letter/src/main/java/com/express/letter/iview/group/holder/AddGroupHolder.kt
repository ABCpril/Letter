package com.express.letter.iview.group.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.express.letter.bean.GroupItem
import com.express.letter.iview.group.JoinGroupUIView

/**
 * Created by angcyo on 2018/03/25 08:47
 */
class AddGroupHolder : BaseGroupHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: GroupItem) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.tv(R.id.text_view).text = "加入群聊"
        holder.imgV(R.id.glide_image_view).setImageResource(R.drawable.ico_add_group)

        holder.clickItem {
            startIView(JoinGroupUIView())
        }
    }
}