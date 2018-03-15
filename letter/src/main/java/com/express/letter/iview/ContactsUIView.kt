package com.express.letter.iview

import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.express.letter.base.BaseSingleRecyclerUIView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：联系人界面
 * 创建人员：Robi
 * 创建时间：2018/03/13 14:42
 * 修改人员：Robi
 * 修改时间：2018/03/13 14:42
 * 修改备注：
 * Version: 1.0.0
 */
class ContactsUIView : BaseSingleRecyclerUIView<String>() {
    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("联系人")
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun createAdapter(): RExBaseAdapter<String, String, String> {
        return object : RExBaseAdapter<String, String, String>(mActivity) {
        }
    }

}