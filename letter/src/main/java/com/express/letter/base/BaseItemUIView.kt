package com.express.letter.base

import android.graphics.Color
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.base.UIItemUIView
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RRecyclerView
import com.express.letter.R

/**
 * Created by angcyo on 2018/02/13 23:09
 */
abstract class BaseItemUIView : UIItemUIView<SingleItem>() {

    override fun getTitleBar(): TitleBarPattern? {
        return super.getTitleBar()
                .setShowTitleBarBottomShadow(true)
    }

    override fun getDefaultBackgroundColor(): Int {
        return Color.WHITE
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.setBackgroundColor(getColor(R.color.bg_color))
    }
}
