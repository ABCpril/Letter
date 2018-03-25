package com.express.letter.iview

import android.os.Bundle
import com.angcyo.hyphenate.REM
import com.angcyo.hyphenate.REMContacts
import com.angcyo.realm.RRealm
import com.angcyo.realm.bean.ContactInviteRealm
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.adapter.RBaseAdapter
import com.angcyo.uiview.recycler.adapter.RExItem
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.angcyo.uiview.utils.RUtils
import com.express.letter.R
import com.express.letter.base.BaseExItemUIView
import com.express.letter.bean.ContactsItem
import com.express.letter.holder.*
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.RealmResults

/**
 * Copyright (C) 2016,深圳市红鸟网络科技股份有限公司 All rights reserved.
 * 项目名称：
 * 类的描述：联系人界面
 * 创建人员：Robi
 * 创建时间：2018/03/13 14:42
 * 修改人员：Robi
 * 修改时间：2018/03/13 14:42
 * 修改备注：
 * Version: 1.0.0
 */
open class ContactsUIView : BaseExItemUIView<ContactsItem>() {

    lateinit var contactInviteRealmResults: RealmResults<ContactInviteRealm>
    private val onContactInviteResultChangeListener = OrderedRealmCollectionChangeListener<RealmResults<ContactInviteRealm>> { _, _ ->
        //更新好友验证 数量
        //L.e("call: onViewLoad -> OrderedRealmCollectionChangeListener:${contactInviteRealmResults.size}:${result.size} insertions:${changeSet.insertions.size}")
        mExBaseAdapter.notifyItemChanged(1)
    }

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("联系人")
    }

    override fun getDefaultLayoutState(): LayoutState {
        return LayoutState.CONTENT
    }

    override fun needLoadData(): Boolean {
        return true
    }

    override fun onViewLoad() {
        super.onViewLoad()
        RRealm.where {
            contactInviteRealmResults = it.where(ContactInviteRealm::class.java)
                    .equalTo("to_username", REM.getCurrentUserName())
                    .findAll()
        }
    }

    override fun onViewShow(bundle: Bundle?, fromClz: Class<*>?) {
        super.onViewShow(bundle, fromClz)
        contactInviteRealmResults.addChangeListener(onContactInviteResultChangeListener)
    }

    override fun onViewShowNotFirst(bundle: Bundle?) {
        super.onViewShowNotFirst(bundle)
        onBaseLoadData()
    }

    override fun onViewHide() {
        super.onViewHide()
        contactInviteRealmResults.removeChangeListener(onContactInviteResultChangeListener)
        contactInviteRealmResults.removeAllChangeListeners()
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        val datas = mutableListOf<ContactsItem>()
        datas.add(ContactsItem("添加好友", ContactsItem.ADD))
        datas.add(ContactsItem("好友验证", ContactsItem.ACCEPT))
        datas.add(ContactsItem("群聊", ContactsItem.GROUP))

        mExBaseAdapter.resetData(datas)
    }

    override fun onUILoadData(page: Int, extend: String?) {
        //super.onUILoadData(page, extend)

        REMContacts.getAllContactsFromServer {
            val datas = mutableListOf<ContactsItem>()

            datas.add(ContactsItem("添加好友", ContactsItem.ADD))
            datas.add(ContactsItem("好友验证", ContactsItem.ACCEPT))
            datas.add(ContactsItem("群聊", ContactsItem.GROUP))

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

    override fun onCreateItemHolder(itemHolder: RExItemHolder<ContactsItem>) {
        super.onCreateItemHolder(itemHolder)
        if (itemHolder is BaseContactsHolder) {
            itemHolder.contactsUIView = this
        }
    }

    override fun registerItems(allRegItems: ArrayList<RExItem<String, ContactsItem>>) {
        allRegItems.add(RExItem(ContactsItem.NORMAL, R.layout.item_contacts_layout, ContactsHolder::class.java))
        allRegItems.add(RExItem(ContactsItem.EMPTY, R.layout.item_contacts_empty_layout, ContactsEmptyHolder::class.java))
        allRegItems.add(RExItem(ContactsItem.ADD, R.layout.item_contacts_sys_layout, ContactsAddHolder::class.java))
        allRegItems.add(RExItem(ContactsItem.ACCEPT, R.layout.item_contacts_sys_layout, ContactsAcceptHolder::class.java))
        allRegItems.add(RExItem(ContactsItem.GROUP, R.layout.item_contacts_sys_layout, ContactsGroupHolder::class.java))
    }

    override fun getItemTypeFromData(data: ContactsItem): String {
        return data.type
    }
}