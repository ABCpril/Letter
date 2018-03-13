package com.express.letter.iview

import com.angcyo.hyphenate.REM
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.kotlin.checkEmpty
import com.angcyo.uiview.kotlin.setInputText
import com.angcyo.uiview.kotlin.string
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.RException
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.Tip
import com.express.letter.R
import com.express.letter.base.BaseItemUIView
import com.express.letter.http.BaseSubscriber
import com.express.letter.util.RHawk

/**
 * Created by angcyo on 2018-03-04.
 */
class LoginUIView : BaseItemUIView() {

    override fun getTitleBar(): TitleBarPattern {
        return super.getTitleBar()
                .setTitleString("登录")
    }

    override fun haveSoftInput(): Boolean {
        return true
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val username = holder.eV(R.id.username)
                val password = holder.eV(R.id.password)

                holder.click(R.id.register_button) {
                    startIView(RegisterUIView())
                }
                username.setInputText(RHawk.getLoginUser())

                holder.click(R.id.login_button) {

                    if (username.checkEmpty() || password.checkEmpty()) {

                    } else {
                        REM.login(username.string(), password.string(),
                                object : BaseSubscriber<String>() {
                                    override fun onStart() {
                                        super.onStart()
                                        UILoading.progress(mParentILayout).setLoadingTipText("登录中...")
                                    }

                                    override fun onSucceed(bean: String?) {
                                        super.onSucceed(bean)
                                        Tip.tip("登录成功")
                                        RHawk.saveLoginUser(username.string())
                                        //finishIView()
                                        replaceIView(MainUIView())
                                    }

                                    override fun onError(code: Int, msg: String?) {
                                        super.onError(code, msg)
                                    }

                                    override fun onEnd(isError: Boolean, isNoNetwork: Boolean, e: RException?) {
                                        super.onEnd(isError, isNoNetwork, e)
                                        UILoading.hide()
                                    }
                                })
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.em_activity_login
            }
        })
    }

}
