package com.express.letter.iview

import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.express.letter.base.BaseItemUIView

/**
 * Created by angcyo on 2018-03-04.
 */
class LoginUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar().setTitleString("登录")
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                holder.click(R.id.register_button) {
                    startIView(RegisterUIView())
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.em_activity_login
            }
        })
    }

}
