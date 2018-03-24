package com.express.letter.iview

import android.text.TextUtils
import com.angcyo.hyphenate.REM
import com.angcyo.realm.RRealm
import com.angcyo.realm.bean.ContactInviteRealm
import com.angcyo.uiview.recycler.adapter.RExItem
import com.express.letter.R
import com.express.letter.base.BaseExItemUIView
import com.express.letter.holder.AcceptContactsHolder

/**
 * 同意好友邀请界面
 * Created by angcyo on 2018/03/24 06:45
 */
class AcceptContactsUIView : BaseExItemUIView<ContactInviteRealm>() {

    override fun getTitleString(): String {
        return "好友验证"
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, ContactInviteRealm>>) {
        allRegItems.add(RExItem("", R.layout.item_accept_contacts, AcceptContactsHolder::class.java))
        allRegItems.add(RExItem("empty", R.layout.item_accept_contacts_empty_layout, AcceptContactsEmptyHolder::class.java))
    }

    override fun getItemTypeFromData(data: ContactInviteRealm): String {
        if (TextUtils.isEmpty(data.username)) {
            return "empty"
        }
        return ""
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)
        RRealm.where {
            val realmResults = it.where(ContactInviteRealm::class.java)
                    .equalTo("to_username", REM.getCurrentUserName())
                    .findAll()
            if (realmResults.isEmpty()) {
                onUILoadFinish(listOf(ContactInviteRealm()))
            } else {
                onUILoadFinish(realmResults)
            }

        }
    }

}
