package com.express.letter.iview.group.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.express.letter.bean.GroupItem

/**
 * Created by angcyo on 2018/03/25 09:23
 */
class GroupHolder : BaseGroupHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: GroupItem) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.tv(R.id.text_view).text = dataBean.emGroup.groupName
        holder.imgV(R.id.glide_image_view).setImageResource(R.drawable.ico_group_avatar)

        holder.clickItem {
            //打开群聊
        }
    }
}