package com.express.letter.iview

import com.angcyo.hyphenate.REMConversation
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.adapter.RExItem
import com.angcyo.uiview.utils.RUtils
import com.express.letter.R
import com.express.letter.base.BaseExItemUIView
import com.express.letter.bean.ConversationItem
import com.express.letter.bean.ConversationItem.EMPTY
import com.express.letter.bean.ConversationItem.NORMAL
import com.express.letter.holder.SessionEmptyHolder
import com.express.letter.holder.SessionHolder

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：会话界面
 * 创建人员：Robi
 * 创建时间：2018/03/13 14:42
 * 修改人员：Robi
 * 修改时间：2018/03/13 14:42
 * 修改备注：
 * Version: 1.0.0
 */
class SessionUIView : BaseExItemUIView<ConversationItem>() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("消息")
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun needLoadData(): Boolean {
        return true
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)

        postDelayed(300) {
            resetUI()
        }

        val allConversations = REMConversation.getAllConversations()

        if (RUtils.isListEmpty(allConversations)) {
            mExBaseAdapter.resetDataData(ConversationItem(EMPTY))
        } else {
            val datas = mutableListOf<ConversationItem>()
            for (c in allConversations) {
                datas.add(ConversationItem(c))
            }
            mExBaseAdapter.resetData(datas)
        }
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, ConversationItem>>) {
        allRegItems.add(RExItem(NORMAL, R.layout.item_session_layout, SessionHolder::class.java))
        allRegItems.add(RExItem(EMPTY, R.layout.item_session_empty_layout, SessionEmptyHolder::class.java))
    }

    override fun getItemTypeFromData(data: ConversationItem): String {
        return data.ConversationType
    }
}