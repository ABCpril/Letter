package com.express.letter

import android.Manifest
import android.content.Intent
import com.angcyo.uiview.RCrashHandler
import com.angcyo.uiview.base.UILayoutActivity
import com.express.letter.iview.LoginUIView
import com.express.letter.iview.MainUIView
import com.express.letter.util.RHawk

class MainActivity : UILayoutActivity() {
    override fun onLoadView(intent: Intent?) {
        checkPermissions()

        if (RHawk.isLoginSucceed()) {
            startIView(MainUIView(), false)
        } else {
            startIView(LoginUIView())
        }

        RCrashHandler.checkCrash(mLayout)
    }

    override fun needPermissions(): Array<String> {
        return arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA)
    }
}
