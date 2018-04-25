package com.express.letter.iview.group

import android.os.Bundle
import com.angcyo.hyphenate.REMGroupManager
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.adapter.RExItem
import com.express.letter.R
import com.express.letter.base.BaseExItemUIView
import com.express.letter.bean.GroupItem
import com.express.letter.iview.group.holder.AddGroupHolder
import com.express.letter.iview.group.holder.GroupHolder
import com.express.letter.iview.group.holder.NewGroupHolder

/**
 * 我加入的群列表
 * Created by angcyo on 2018/03/25 08:39
 */
class GroupListUIView : BaseExItemUIView<GroupItem>() {

    override fun getTitleString(): String {
        return "群聊"
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, GroupItem>>) {
        allRegItems.add(RExItem(GroupItem.NORMAL, R.layout.item_group_layout, GroupHolder::class.java))
        allRegItems.add(RExItem(GroupItem.ADD_GROUP, R.layout.item_group_layout, AddGroupHolder::class.java))
        allRegItems.add(RExItem(GroupItem.NEW_GROUP, R.layout.item_group_layout, NewGroupHolder::class.java))
    }

    override fun getItemTypeFromData(data: GroupItem): String {
        return data.type
    }

    override fun needLoadData(): Boolean {
        return true
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val datas = mutableListOf<GroupItem>()
        datas.add(GroupItem(GroupItem.NEW_GROUP))
        datas.add(GroupItem(GroupItem.ADD_GROUP))
        onUILoadFinish(datas)
    }

    override fun onViewShowNotFirst(bundle: Bundle?) {
        super.onViewShowNotFirst(bundle)
        onBaseLoadData()
    }

    override fun initAdapter(baseAdapter: RExBaseAdapter<String, GroupItem, String>) {
        super.initAdapter(baseAdapter)
        baseAdapter.isAutoEnableLoadMore = false
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        add(REMGroupManager.getJoinedGroupsFromServer {
            val datas = mutableListOf<GroupItem>()
            datas.add(GroupItem(GroupItem.NEW_GROUP))
            datas.add(GroupItem(GroupItem.ADD_GROUP))
            it.mapTo(datas) { GroupItem(it) }

            onUILoadFinish(datas)
            ""
        })
    }

}