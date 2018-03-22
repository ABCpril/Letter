package com.express.letter.iview

import android.os.Bundle
import com.angcyo.uiview.RApplication
import com.angcyo.uiview.base.PageBean
import com.angcyo.uiview.base.UINavigationView
import com.angcyo.uiview.skin.SkinHelper
import com.express.letter.R

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：
 * 创建人员：Robi
 * 创建时间：2018/03/13 14:35
 * 修改人员：Robi
 * 修改时间：2018/03/13 14:35
 * 修改备注：
 * Version: 1.0.0
 */
class MainUIView : UINavigationView() {
    override fun createPages(pages: ArrayList<PageBean>) {
        pages.add(PageBean(com.express.letter.iview.SessionUIView(), "消息",
                RApplication.getApp().resources.getColor(com.angcyo.uiview.R.color.base_text_color), SkinHelper.getSkin().themeColor,
                R.drawable.session, R.drawable.session_c))

        pages.add(PageBean(com.express.letter.iview.ContactsUIView(), "联系人",
                RApplication.getApp().resources.getColor(com.angcyo.uiview.R.color.base_text_color), SkinHelper.getSkin().themeColor,
                R.drawable.contacts, R.drawable.contacts_c))

        pages.add(PageBean(com.express.letter.iview.MeUIView(), "我的",
                RApplication.getApp().resources.getColor(com.angcyo.uiview.R.color.base_text_color), SkinHelper.getSkin().themeColor,
                R.drawable.me, R.drawable.me_c))
    }

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
//        showNoReadNum(0, 1)
//        showNoReadNum(1, 200)
//        showNoReadNum(2, 99)
    }
}