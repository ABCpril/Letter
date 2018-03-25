package com.express.letter.iview.group.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.express.letter.bean.GroupItem

/**
 * Created by angcyo on 2018/03/25 08:47
 */
class NewGroupHolder : BaseGroupHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: GroupItem) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.tv(R.id.text_view).text = "创建群聊"

        holder.imgV(R.id.glide_image_view).setImageResource(R.drawable.ico_new_group)

        holder.clickItem {

        }
    }
}