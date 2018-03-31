package com.express.letter.iview

import com.angcyo.hyphenate.REM
import com.angcyo.hyphenate.REMContacts
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.kotlin.onTextChanage
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.Tip
import com.express.letter.R
import com.express.letter.base.BaseItemUIView

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：添加好友
 * 创建人员：Robi
 * 创建时间：2018/03/22 17:13
 * 修改人员：Robi
 * 修改时间：2018/03/22 17:13
 * 修改备注：
 * Version: 1.0.0
 */
class AddContactsUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern? {
        return super.getTitleBar()!!.setTitleString("添加好友")
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.exV(R.id.edit_text_view).apply {
                    post {
                        showSoftInput()
                    }
                    onTextChanage { username ->
                        if (username.isEmpty()) {
                            holder.invisible(R.id.user_control_layout)
                        } else {
                            holder.visible(R.id.user_control_layout)
                            holder.tv(R.id.text_view).text = username

                            REMContacts.isContacts(username) {
                                if (it) {
                                    holder.tv(R.id.button_view).text = "已是好友"
                                    holder.click(R.id.button_view) {

                                    }
                                } else {
                                    holder.tv(R.id.button_view).text = "添加好友"
                                    holder.click(R.id.button_view) {
                                        REMContacts.addContact(username, "${REM.getCurrentUserName()} 请求添加您为好友") {
                                            if (it == null) {
                                                Tip.tip("添加失败")
                                            } else {
                                                Tip.tip("已发送申请")
                                            }
                                            ""
                                        }
                                    }
                                }
                                ""
                            }
                        }
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_add_contacts
            }
        })
    }

}