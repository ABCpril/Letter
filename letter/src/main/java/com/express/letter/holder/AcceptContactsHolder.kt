package com.express.letter.holder

import android.text.TextUtils
import com.angcyo.hyphenate.REMContacts
import com.angcyo.realm.RRealm
import com.angcyo.realm.bean.ContactInviteRealm
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.angcyo.uiview.utils.Tip
import com.express.letter.R

/**
 * Created by angcyo on 2018/03/24 06:52
 */
class AcceptContactsHolder : RExItemHolder<ContactInviteRealm>() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: ContactInviteRealm) {
        holder.tv(R.id.text_view).text = dataBean.username
        holder.tv(R.id.tip_view).text = when (dataBean.statue) {
            1 -> {
                holder.gone(R.id.button_control_layout)
                "已同意"
            }
            2 -> {
                holder.gone(R.id.button_control_layout)
                "已拒绝"
            }
            else -> {
                holder.visible(R.id.button_control_layout)
                dataBean.reason
            }
        }

        holder.click(R.id.accept_button) {
            UILoading.show2(iLayout)
                    .addDismiss(REMContacts.acceptInvitation(dataBean.username) {
                        UILoading.hide()
                        if (TextUtils.isEmpty(it)) {
                            Tip.tip("失败")
                        } else {
                            RRealm.exe {
                                dataBean.statue = 1
                                exItemAdapter?.notifyItemChanged(posInData)
                            }
                        }
                        ""
                    })
        }

        holder.click(R.id.decline_button) {
            UILoading.show2(iLayout)
                    .addDismiss(REMContacts.declineInvitation(dataBean.username) {
                        UILoading.hide()
                        if (TextUtils.isEmpty(it)) {
                            Tip.tip("失败")
                        } else {
                            RRealm.exe {
                                dataBean.statue = 2
                                exItemAdapter?.notifyItemChanged(posInData)
                            }
                        }
                        ""
                    })
        }
    }
}