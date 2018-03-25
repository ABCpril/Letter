package com.express.letter.iview

import android.graphics.Color
import com.angcyo.hyphenate.REM
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.container.ContentLayout
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.RRecyclerView
import com.express.letter.BuildConfig
import com.express.letter.R
import com.express.letter.base.BaseItemUIView
import com.express.letter.http.BaseSubscriber

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/13 14:44
 * 修改人员：Robi
 * 修改时间：2018/03/13 14:44
 * 修改备注：
 * Version: 1.0.0
 */
class MeUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("我")
                .setShowTitleBarBottomShadow(false)
    }

    override fun initRecyclerView(recyclerView: RRecyclerView?, baseContentLayout: ContentLayout?) {
        super.initRecyclerView(recyclerView, baseContentLayout)
        recyclerView?.setBackgroundColor(Color.TRANSPARENT)
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.tv(R.id.user_name).text = REM.getCurrentUserName()
                holder.tv(R.id.button_view).text = "退出登录"
                holder.click(R.id.button_view) {
                    REM.logout(object : BaseSubscriber<String>() {
                        override fun onSucceed(bean: String?) {
                            super.onSucceed(bean)
                            mParentILayout.replaceIView(LoginUIView())
                        }
                    })
                }

                if (BuildConfig.DEBUG) {
                    holder.longClick(R.id.button_view) {
                        val i = 1 / 0
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_me_top
            }
        })
    }
}