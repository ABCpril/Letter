package com.express.letter.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExItemHolder
import com.express.letter.R
import com.express.letter.bean.ContactsItem
import com.express.letter.iview.ContactsUIView

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
open class BaseContactsHolder : RExItemHolder<ContactsItem>() {

    var contactsUIView: ContactsUIView? = null

    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: ContactsItem) {
        holder.tv(R.id.text_view).text = dataBean.username
    }

}