package com.express.letter.holder

import com.angcyo.uiview.manager.RNotifier
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.widget.NoReadNumView
import com.express.letter.R
import com.express.letter.bean.ContactsItem

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/21 17:58
 * 修改人员：Robi
 * 修改时间：2018/03/21 17:58
 * 修改备注：
 * Version: 1.0.0
 */
class ContactsAcceptHolder : BaseContactsHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: ContactsItem) {
        super.onBindItemDataView(holder, posInData, dataBean)
        holder.imgV(R.id.glide_image_view).setImageResource(R.drawable.ico_accept)
        val noReadNumView: NoReadNumView = holder.v(R.id.no_read_num_view)
//        noReadNumView.noReadNum = 0
//
//        val noReadNumView2: NoReadNumView = holder.v(R.id.no_read_num_view2)
//        noReadNumView2.noReadNum = 99
//
//        val noReadNumView3: NoReadNumView = holder.v(R.id.no_read_num_view3)
//        noReadNumView3.noReadNum = 100
//
//        val noReadNumView4: NoReadNumView = holder.v(R.id.no_read_num_view4)
//        noReadNumView4.noReadNum = 9
//
//        holder.postDelay(2000) {
//            noReadNumView.noReadNum = 100
//            noReadNumView3.noReadNum = 1
//        }

        holder.clickItem {
            RNotifier.instance().vibrateAndPlayTone()
        }
    }

}