package com.express.letter.iview.group

import com.angcyo.hyphenate.REMGroupManager
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.kotlin.toStringArray
import com.angcyo.uiview.model.TitleBarItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.T_
import com.angcyo.uiview.utils.Tip
import com.angcyo.uiview.widget.ExEditText
import com.express.letter.R
import com.express.letter.base.BaseItemUIView
import com.express.letter.iview.ContactsSelectorUIView
import com.hyphenate.chat.EMGroupManager
import com.hyphenate.chat.EMGroupOptions

/**
 * 创建群聊
 * Created by angcyo on 2018/03/25 10:10
 */
class NewGroupUIVIew : BaseItemUIView() {

    lateinit var groupNameView: ExEditText
    lateinit var groupDesView: ExEditText

    override fun getTitleBar(): TitleBarPattern? {
        return super.getTitleBar()!!
                .setTitleString("创建群聊")
                .addRightItem(TitleBarItem.build("创建") {
                    if (groupNameView.checkEmpty()) {
                        T_.error("请输入群聊名称")
                    } else {
                        startIView(ContactsSelectorUIView().apply {
                            onSelectorResult = {
                                UILoading.progress(mParentILayout)
                                        .setLoadingTipText("正在创建群聊")
                                        .addDismiss(REMGroupManager.createGroup(groupNameView.string(), groupDesView.string(), "",
                                                EMGroupOptions().apply {
                                                    maxUsers = 200
                                                    style = EMGroupManager.EMGroupStyle.EMGroupStylePublicJoinNeedApproval
                                                }, it.toStringArray(), {
                                            UILoading.hide()
                                            if (it) {
                                                this@NewGroupUIVIew.finishIView()
                                            } else {
                                                Tip.tip("创建群聊失败")
                                            }
                                            ""
                                        }))
                            }
                        })
                    }
                })
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                groupNameView = holder.v(R.id.group_name_edit)
                groupDesView = holder.v(R.id.group_des_edit)

                groupNameView.post {
                    showSoftInput(groupNameView)
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.item_create_new_group_layout
            }
        })
    }

}