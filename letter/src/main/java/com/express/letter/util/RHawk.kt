package com.express.letter.util

import com.orhanobut.hawk.Hawk

/**
 * Created by angcyo on 2018-03-06.
 */
object RHawk {

    var saveLoginUser: String
        get() {
            return Hawk.get("saveLoginUser", "")
        }
        set(value) {
            Hawk.put("saveLoginUser", value)
        }

    var loginPassword: String
        get() {
            return Hawk.get("loginPassword", "")
        }
        set(value) {
            Hawk.put("loginPassword", value)
        }

    fun isLoginSucceed() = Hawk.get<Boolean>("isLoginSucceed", false)

    fun setLoginSucceed(succeed: Boolean) = Hawk.put<Boolean>("isLoginSucceed", succeed)
}
