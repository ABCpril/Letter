package com.express.letter.iview.call

import android.content.Context
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.media.SoundPool
import android.os.Bundle
import com.angcyo.uiview.model.TitleBarPattern
import com.express.letter.R
import com.express.letter.base.BaseItemUIView
import com.hyphenate.EMCallBack
import com.hyphenate.chat.*
import com.hyphenate.util.EMLog
import java.util.*

/**
 * Created by angcyo on 2018/03/31 09:32
 */
open abstract class BaseCallUIView(val username: String, val isCallTo: Boolean = true /*主叫方*/) : BaseItemUIView() {

    var onSaveMessage: ((EMMessage) -> Unit)? = null

    companion object {
        val MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call"
        val MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call"
        val TAG = "BaseCallUIView"
    }

    override fun getTitleBar(): TitleBarPattern? = null

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onViewShow(bundle: Bundle?, fromClz: Class<*>?) {
        super.onViewShow(bundle, fromClz)
        keepScreenOn(true)
    }

    override fun onViewHide() {
        super.onViewHide()
        keepScreenOn(false)
    }

    override fun initOnShowContentLayout() {
        super.initOnShowContentLayout()
        initSound()
    }

    protected var pushProvider: EMCallManager.EMCallPushProvider? = null

    override fun onViewLoad() {
        super.onViewLoad()
        audioManager = mActivity.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        pushProvider = object : EMCallManager.EMCallPushProvider {

            internal fun updateMessageText(oldMsg: EMMessage, to: String) {
                // update local message text
                val conv = EMClient.getInstance().chatManager().getConversation(oldMsg.to)
                conv.removeMessage(oldMsg.msgId)
            }

            override fun onRemoteOffline(to: String) {

                //this function should exposed & move to Demo
                EMLog.d(TAG, "onRemoteOffline, to:$to")

                val message = EMMessage.createTxtSendMessage("You have an incoming call", to)
                // set the user-defined extension field
                message.setAttribute("em_apns_ext", true)

                message.setAttribute("is_voice_call", if (callType == 0) true else false)

                message.setMessageStatusCallback(object : EMCallBack {

                    override fun onSuccess() {
                        EMLog.d(TAG, "onRemoteOffline success")
                        updateMessageText(message, to)
                    }

                    override fun onError(code: Int, error: String) {
                        EMLog.d(TAG, "onRemoteOffline Error")
                        updateMessageText(message, to)
                    }

                    override fun onProgress(progress: Int, status: String) {}
                })
                // send messages
                EMClient.getInstance().chatManager().sendMessage(message)
            }
        }

        EMClient.getInstance().callManager().setPushProvider(pushProvider)
    }

    protected var callStateListener: EMCallStateChangeListener? = null
    override fun onViewUnload() {
        super.onViewUnload()
        soundPool?.release()
        if (ringtone != null && ringtone!!.isPlaying)
            ringtone!!.stop()
        audioManager!!.mode = AudioManager.MODE_NORMAL
        audioManager!!.isMicrophoneMute = false

        if (callStateListener != null)
            EMClient.getInstance().callManager().removeCallStateChangeListener(callStateListener)

        if (pushProvider != null) {
            EMClient.getInstance().callManager().setPushProvider(null)
            pushProvider = null
        }
    }


    protected var streamID = -1
    override fun onViewShowFirst(bundle: Bundle?) {
        super.onViewShowFirst(bundle)
        if (isCallTo) {
            streamID = playMakeCallSounds()
        }
    }

    protected var audioManager: AudioManager? = null
    protected var soundPool: SoundPool? = null
    protected var ringtone: Ringtone? = null
    protected var outgoing: Int = 0
    private fun initSound() {
        if (isCallTo) {// outgoing call
            soundPool = SoundPool(1, AudioManager.STREAM_RING, 0)
            outgoing = soundPool!!.load(mActivity, R.raw.em_outgoing, 1)
        } else { // incoming call
            val ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            audioManager!!.mode = AudioManager.MODE_RINGTONE
            audioManager!!.isSpeakerphoneOn = true
            ringtone = RingtoneManager.getRingtone(mActivity, ringUri)
            ringtone!!.play()
        }
    }

    /**
     * play the incoming call ringtone
     *
     */
    protected fun playMakeCallSounds(): Int {
        try {
            audioManager!!.mode = AudioManager.MODE_RINGTONE
            audioManager!!.isSpeakerphoneOn = true

            // play
            return soundPool!!.play(outgoing, // sound resource
                    0.3f, // left volume
                    0.3f, // right volume
                    1, // priority
                    -1, // loop，0 is no loop，-1 is loop forever
                    1f)
        } catch (e: Exception) {
            return -1
        }
    }

    /**挂断*/
    protected fun callEnd() {
        soundPool?.stop(streamID)
        EMLog.d("EMCallManager", "soundPool stop MSG_CALL_END")
        try {
            EMClient.getInstance().callManager().endCall()
        } catch (e: Exception) {
            saveCallRecord()
            finishIView()
        }
    }

    /**拒接*/
    protected fun rejectCall() {
        callingState = CallingState.REFUSED
        ringtone?.stop()
        try {
            EMClient.getInstance().callManager().rejectCall()
        } catch (e1: Exception) {
            e1.printStackTrace()
            saveCallRecord()
            finishIView()
        }
    }

    protected var isAnswered = false

    /**接听*/
    protected fun answerCall() {
        EMLog.d(TAG, "MSG_CALL_ANSWER")
        ringtone?.stop()
        if (!isCallTo) {
            try {
                EMClient.getInstance().callManager().answerCall()
                isAnswered = true
                // meizu MX5 4G, hasDataConnection(context) return status is incorrect
                // MX5 con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected() return false in 4G
                // so we will not judge it, App can decide whether judge the network status

                //                        if (NetUtils.hasDataConnection(CallActivity.this)) {
                //                            EMClient.getInstance().callManager().answerCall();
                //                            isAnswered = true;
                //                        } else {
                //                            runOnUiThread(new Runnable() {
                //                                public void run() {
                //                                    final String st2 = getResources().getString(R.string.Is_not_yet_connected_to_the_server);
                //                                    Toast.makeText(CallActivity.this, st2, Toast.LENGTH_SHORT).show();
                //                                }
                //                            });
                //                            throw new Exception();
                //                        }
            } catch (e: Exception) {
                e.printStackTrace()
                saveCallRecord()
                finishIView()
                return
            }

        } else {
            EMLog.d(TAG, "answer call isInComingCall:false")
        }
    }

    /**拨打时长*/
    protected var callDruationText: String? = null

    protected var callingState = CallingState.CANCELLED
    /**
     * 0：voice call，1：video call
     */
    protected var callType = 0

    /**
     * save call record
     */
    protected fun saveCallRecord() {
        var message: EMMessage? = null
        var txtBody: EMTextMessageBody? = null
        if (isCallTo) { // outgoing call
            message = EMMessage.createSendMessage(EMMessage.Type.TXT)
            message!!.to = username
        } else {
            message = EMMessage.createReceiveMessage(EMMessage.Type.TXT)
            message!!.from = username
        }

        val st1 = resources.getString(R.string.call_duration)
        val st2 = resources.getString(R.string.Refused)
        val st3 = resources.getString(R.string.The_other_party_has_refused_to)
        val st4 = resources.getString(R.string.The_other_is_not_online)
        val st5 = resources.getString(R.string.The_other_is_on_the_phone)
        val st6 = resources.getString(R.string.The_other_party_did_not_answer)
        val st7 = resources.getString(R.string.did_not_answer)
        val st8 = resources.getString(R.string.Has_been_cancelled)
        when (callingState) {
            CallingState.NORMAL -> txtBody = EMTextMessageBody(st1 + callDruationText)
            CallingState.REFUSED -> txtBody = EMTextMessageBody(st2)
            CallingState.BEREFUSED -> txtBody = EMTextMessageBody(st3)
            CallingState.OFFLINE -> txtBody = EMTextMessageBody(st4)
            CallingState.BUSY -> txtBody = EMTextMessageBody(st5)
            CallingState.NO_RESPONSE -> txtBody = EMTextMessageBody(st6)
            CallingState.UNANSWERED -> txtBody = EMTextMessageBody(st7)
            CallingState.VERSION_NOT_SAME -> txtBody = EMTextMessageBody(getString(R.string.call_version_inconsistent))
            else -> txtBody = EMTextMessageBody(st8)
        }
        // set message extension
        if (callType == 0)
            message.setAttribute(MESSAGE_ATTR_IS_VOICE_CALL, true)
        else
            message.setAttribute(MESSAGE_ATTR_IS_VIDEO_CALL, true)

        // set message body
        message.addBody(txtBody)
        message.msgId = msgid
        message.setStatus(EMMessage.Status.SUCCESS)

        // save
        EMClient.getInstance().chatManager().saveMessage(message)

        onSaveMessage?.invoke(message)
    }

    protected var msgid: String = UUID.randomUUID().toString()

    /**打开扬声器*/
    protected fun openSpeakerOn() {
        try {
            if (!audioManager!!.isSpeakerphoneOn)
                audioManager!!.isSpeakerphoneOn = true
            audioManager!!.mode = AudioManager.MODE_IN_COMMUNICATION
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**关闭扬声器*/
    protected fun closeSpeakerOn() {
        try {
            if (audioManager != null) {
                // int curVolume =
                // audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
                if (audioManager!!.isSpeakerphoneOn)
                    audioManager!!.isSpeakerphoneOn = false
                audioManager!!.mode = AudioManager.MODE_IN_COMMUNICATION
                // audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
                // curVolume, AudioManager.STREAM_VOICE_CALL);
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

enum class CallingState {
    CANCELLED, NORMAL, REFUSED, BEREFUSED, UNANSWERED, OFFLINE, NO_RESPONSE, BUSY, VERSION_NOT_SAME
}