package com.express.letter.base

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.angcyo.uiview.base.UIStringItemUIView
import com.angcyo.uiview.widget.EmptyView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/21 16:58
 * 修改人员：Robi
 * 修改时间：2018/03/21 16:58
 * 修改备注：
 * Version: 1.0.0
 */
abstract class BaseExItemUIView<T> : UIStringItemUIView<T>() {
    override fun inflateLoadLayout(baseRootLayout: FrameLayout?, inflater: LayoutInflater?): View {
        val emptyView: EmptyView = super.inflateLoadLayout(baseRootLayout, inflater) as EmptyView
        emptyView.setShowType(EmptyView.SHOW_TYPE_2)
        return emptyView
    }
}