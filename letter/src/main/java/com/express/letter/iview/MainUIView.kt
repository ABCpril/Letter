package com.express.letter.iview

import android.os.Bundle
import com.angcyo.hyphenate.REM
import com.angcyo.hyphenate.REMConversation
import com.angcyo.hyphenate.REMMessage
import com.angcyo.hyphenate.listener.REMMessageListener
import com.angcyo.realm.RRealm
import com.angcyo.realm.bean.ContactInviteRealm
import com.angcyo.uiview.RApplication
import com.angcyo.uiview.base.PageBean
import com.angcyo.uiview.base.UINavigationView
import com.angcyo.uiview.container.UIParam
import com.angcyo.uiview.skin.SkinHelper
import com.express.letter.R
import com.hyphenate.chat.EMMessage

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

    val messageListener = object : REMMessageListener() {
        override fun onNewMessage(messages: MutableList<EMMessage>) {
            super.onNewMessage(messages)
            updateNoReamMessageNum()
        }
    }

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

    override fun onViewShow(bundle: Bundle?, fromClz: Class<*>?) {
        super.onViewShow(bundle, fromClz)
        updateNoReamMessageNum()
    }

    /**更新未读消息数量*/
    fun updateNoReamMessageNum() {
        val msgCount = REMConversation.getAllUnreadMsgCount()
        showNoReadNum(0, if (msgCount == 0) -1 else msgCount)

        RRealm.where {
            val count = it.where(ContactInviteRealm::class.java)
                    .equalTo("to_username", REM.getCurrentUserName())
                    .findAll().count { it.statue == 0 }

            showNoReadNum(1, if (count == 0) -1 else count)
        }
    }

    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
//        showNoReadNum(0, 1)
//        showNoReadNum(1, 200)
//        showNoReadNum(2, 99)
    }

    override fun onViewLoad() {
        super.onViewLoad()
        REMMessage.addMessageListener(messageListener)
    }

    override fun onViewUnload(uiParam: UIParam?) {
        super.onViewUnload(uiParam)
        REMMessage.removeMessageListener(messageListener)
    }
}