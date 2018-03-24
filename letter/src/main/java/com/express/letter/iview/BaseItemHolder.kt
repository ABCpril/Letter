package com.express.letter.iview

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExItemHolder

/**
 * Created by angcyo on 2018/03/24 08:18
 */
open class BaseItemHolder<T> : RExItemHolder<T>() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: T) {

    }
}