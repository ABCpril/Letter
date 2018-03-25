package com.express.letter.iview.group

import android.text.TextUtils
import com.angcyo.hyphenate.REMGroupManager
import com.angcyo.uiview.recycler.adapter.RExItem
import com.express.letter.R
import com.express.letter.base.BaseExItemUIView
import com.express.letter.iview.group.holder.JoinGroupEmptyHolder
import com.express.letter.iview.group.holder.JoinGroupHolder
import com.hyphenate.chat.EMGroupInfo

/**
 * 加入群聊
 * Created by angcyo on 2018/03/25 09:33
 */
class JoinGroupUIView : BaseExItemUIView<EMGroupInfo>() {

    override fun getTitleString(): String {
        return "加入群聊"
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, EMGroupInfo>>) {
        allRegItems.add(RExItem("empty", R.layout.item_empty_layout, JoinGroupEmptyHolder::class.java))
        allRegItems.add(RExItem("normal", R.layout.item_group_layout, JoinGroupHolder::class.java))
    }

    override fun getItemTypeFromData(data: EMGroupInfo): String {
        return if (TextUtils.isEmpty(data.groupId)) {
            "empty"
        } else {
            "normal"
        }
    }

    override fun onUILoadEmpty() {
        //super.onUILoadEmpty()
        showContentLayout()
        mExBaseAdapter.resetDataData(EMGroupInfo("", ""))
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        add(REMGroupManager.getPublicGroupsFromServer {
            onUILoadFinish(it)
            ""
        })
    }

}