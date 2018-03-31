package com.express.letter.iview

import android.text.TextUtils
import com.angcyo.hyphenate.REM
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.dialog.UILoading
import com.angcyo.uiview.kotlin.checkEmpty
import com.angcyo.uiview.kotlin.string
import com.angcyo.uiview.model.TitleBarPattern
import com.angcyo.uiview.net.RException
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.Tip
import com.express.letter.R
import com.express.letter.base.BaseItemUIView
import com.express.letter.http.BaseSubscriber

/**
 * Created by angcyo on 2018-03-04.
 */
class RegisterUIView : BaseItemUIView() {

    var onRegisterSuccess: ((String) -> Unit)? = null

    override fun getTitleBar(): TitleBarPattern? {
        return super.getTitleBar()!!
                .setTitleString("注册")
    }

    override fun haveSoftInput(): Boolean {
        return true
    }

    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                val username = holder.eV(R.id.username)
                val password = holder.eV(R.id.password)
                val password2 = holder.eV(R.id.confirm_password)

                holder.click(R.id.register_button) {
                    if (username.checkEmpty() || password.checkEmpty()) {

                    } else {
                        if (TextUtils.equals(password.string(), password2.string())) {
                            REM.register(username.string(), password.string())
                                    .subscribe(object : BaseSubscriber<String>() {
                                        override fun onStart() {
                                            super.onStart()
                                            UILoading.progress(mParentILayout).setLoadingTipText("注册中...")
                                        }

                                        override fun onSucceed(bean: String?) {
                                            super.onSucceed(bean)
                                            Tip.tip("注册成功")
                                            finishIView {
                                                onRegisterSuccess?.invoke(username.string())
                                            }
                                        }

                                        override fun onEnd(isError: Boolean, isNoNetwork: Boolean, e: RException?) {
                                            super.onEnd(isError, isNoNetwork, e)
                                            UILoading.hide()
                                        }
                                    })
                        } else {
                            Tip.tip("两次密码不一致")
                        }

                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.em_activity_register
            }
        })
    }

}
