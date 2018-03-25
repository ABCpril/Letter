package com.express.letter.iview.group

import com.angcyo.hyphenate.REMGroupManager
import com.angcyo.uiview.recycler.adapter.RExItem
import com.express.letter.R
import com.express.letter.base.BaseExItemUIView
import com.express.letter.iview.group.holder.MemberHolder

/**
 * 群成员列表界面
 * Created by angcyo on 2018/03/25 12:29
 */
class GroupMembersUIView(val groupId: String) : BaseExItemUIView<String>() {

    override fun getTitleString(): String {
        return "成员列表"
    }

    override fun getItemTypeFromData(data: String): String {
        return "member"
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, String>>) {
        allRegItems.add(RExItem("member", R.layout.item_contacts_layout, MemberHolder::class.java))
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        add(REMGroupManager.fetchGroupMembers(groupId) {
            onUILoadFinish(it)
            ""
        })
    }
}