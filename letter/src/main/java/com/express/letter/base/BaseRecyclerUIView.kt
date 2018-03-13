package com.express.letter.base

import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.angcyo.uiview.base.UIRecyclerUIView
import com.angcyo.uiview.widget.EmptyView

/**
 * Created by angcyo on 2018/02/13 23:10
 */
abstract class BaseRecyclerUIView<H, T, F> : UIRecyclerUIView<H, T, F>() {
    override fun inflateLoadLayout(baseRootLayout: FrameLayout?, inflater: LayoutInflater?): View {
        val emptyView: EmptyView = super.inflateLoadLayout(baseRootLayout, inflater) as EmptyView
        emptyView.setShowType(EmptyView.SHOW_TYPE_2)
        return emptyView
    }
}
