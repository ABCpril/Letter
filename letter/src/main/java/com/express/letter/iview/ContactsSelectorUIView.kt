package com.express.letter.iview

import android.view.View
import com.angcyo.hyphenate.REMContacts
import com.angcyo.uiview.base.UIBaseView
import com.angcyo.uiview.model.TitleBarItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.angcyo.uiview.recycler.adapter.RExItem
import com.angcyo.uiview.recycler.adapter.RModelAdapter
import com.angcyo.uiview.utils.RUtils
import com.express.letter.R
import com.express.letter.base.BaseExItemUIView
import com.express.letter.bean.ContactsItem
import com.express.letter.holder.ContactsEmptyHolder
import com.express.letter.holder.ContactsSelectorHolder

/**
 * 选择联系人界面
 * Created by angcyo on 2018/03/25 10:28
 */
class ContactsSelectorUIView : BaseExItemUIView<ContactsItem>() {

    var onSelectorResult: ((ArrayList<String>) -> Unit)? = null

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setTitleString("选择联系人")
                .addRightItem(TitleBarItem.build("确定") {
                    finishIView {
                        val users = ArrayList<String>()
                        mExBaseAdapter.allSelectorData.mapTo(users) { it.username }
                        onSelectorResult?.invoke(users)
                    }
                }.setVisibility(View.GONE).setId(R.id.base_dialog_ok_view))
    }

    override fun getDefaultLayoutState(): UIBaseView.LayoutState {
        return UIBaseView.LayoutState.LOAD
    }

    override fun createAdapter(): RExBaseAdapter<String, ContactsItem, String> {
        return super.createAdapter().apply {
            model = RModelAdapter.MODEL_MULTI
            addOnModelChangeListener(object : RModelAdapter.SingleChangeListener() {
                override fun onSelectorChange(selectorList: MutableList<Int>?) {
                    super.onSelectorChange(selectorList)
                    if (RUtils.isListEmpty(selectorList)) {
                        uiTitleBarContainer.hideRightItemById(R.id.base_dialog_ok_view)
                    } else {
                        uiTitleBarContainer.showRightItemById(R.id.base_dialog_ok_view)
                        tv(R.id.base_dialog_ok_view).text = "确定(${selectorList!!.size})"
                    }
                }
            })
        }
    }

    override fun onUILoadData(page: Int, extend: String?) {
        super.onUILoadData(page, extend)

        REMContacts.getAllContactsFromServer {
            showContentLayout()

            val datas = mutableListOf<ContactsItem>()

            if (RUtils.isListEmpty(it)) {
                datas.add(ContactsItem("暂无联系人...", ContactsItem.EMPTY))
            } else {
                it.mapTo(datas) { ContactsItem(it) }
            }

//            mExBaseAdapter.resetData(datas)

            mExBaseAdapter.resetData(datas, object : RBaseAdapter.RDiffCallback<ContactsItem>() {
                override fun areContentsTheSame(oldData: ContactsItem, newData: ContactsItem): Boolean {
                    if (oldData.type == ContactsItem.ACCEPT || newData.type == ContactsItem.ACCEPT) {
                        return false
                    }
                    return super.areContentsTheSame(oldData, newData)
                }
            })

            resetUI()
            ""
        }
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, ContactsItem>>) {
        allRegItems.add(RExItem(ContactsItem.NORMAL, R.layout.item_contacts_layout, ContactsSelectorHolder::class.java))
        allRegItems.add(RExItem(ContactsItem.EMPTY, R.layout.item_contacts_empty_layout, ContactsEmptyHolder::class.java))
    }

    override fun getItemTypeFromData(data: ContactsItem): String {
        return data.type
    }
}