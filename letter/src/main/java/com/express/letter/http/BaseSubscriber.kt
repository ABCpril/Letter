package com.express.letter.http

import com.angcyo.uiview.net.RSubscriber
import com.angcyo.uiview.utils.T_
import java.util.*

/**
 * Created by angcyo on 2018-03-04.
 */
open class BaseSubscriber<T> : RSubscriber<T>() {
    override fun onError(code: Int, msg: String?) {
        super.onError(code, msg)
        T_.error(String.format(Locale.CHINA, "[%d]%s", code, msg))
    }
}
