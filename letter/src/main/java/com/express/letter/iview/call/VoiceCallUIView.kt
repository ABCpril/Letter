package com.express.letter.iview.call

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.T_
import com.express.letter.R
import com.hyphenate.chat.EMCallStateChangeListener
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException
import com.hyphenate.util.EMLog

/**
 * 语音通话
 * Created by angcyo on 2018/03/31 09:04
 */
class VoiceCallUIView(username: String /*用户*/, isCallTo: Boolean = true /*主叫方*/) : BaseCallUIView(username, isCallTo) {

    init {
        callType = 0
    }

    private var isMuteState: Boolean = false
    private var isHandsfreeState: Boolean = false

    lateinit var callStateTextView: TextView
    lateinit var tvIsP2p: TextView
    lateinit var netwrokStatusVeiw: TextView
    override fun createItems(items: MutableList<SingleItem>) {
        items.add(object : SingleItem() {
            override fun onBindView(holder: RBaseViewHolder, posInData: Int, dataBean: Item?) {
                callStateTextView = holder.v(R.id.tv_call_state)
                tvIsP2p = holder.v(R.id.tv_is_p2p)
                netwrokStatusVeiw = holder.v(R.id.tv_network_status)

                holder.tv(R.id.tv_nick).text = username
                if (isCallTo) {
                    holder.gone(R.id.ll_coming_call)
                    holder.visible(R.id.btn_hangup_call)
                }

                holder.click(R.id.btn_hangup_call) {
                    //挂断
                    callEnd()
                }

                holder.click(R.id.btn_refuse_call) {
                    //拒接
                    rejectCall()
                }

                holder.click(R.id.btn_answer_call) {
                    //接听
                    answerCall()
                }

                //静音
                holder.click(R.id.iv_mute) {
                    if (isMuteState) {
                        (it as ImageView).setImageResource(R.drawable.em_icon_mute_normal)
                        try {
                            EMClient.getInstance().callManager().resumeVoiceTransfer()
                        } catch (e: HyphenateException) {
                            e.printStackTrace()
                        }
                        isMuteState = false
                    } else {
                        (it as ImageView).setImageResource(R.drawable.em_icon_mute_on)
                        try {
                            EMClient.getInstance().callManager().pauseVoiceTransfer()
                        } catch (e: HyphenateException) {
                            e.printStackTrace()
                        }
                        isMuteState = true
                    }
                }

                //免提
                holder.click(R.id.iv_handsfree) {
                    if (isHandsfreeState) {
                        (it as ImageView).setImageResource(R.drawable.em_icon_speaker_normal)
                        closeSpeakerOn()
                        isHandsfreeState = false
                    } else {
                        (it as ImageView).setImageResource(R.drawable.em_icon_speaker_on)
                        openSpeakerOn()
                        isHandsfreeState = true
                    }
                }
            }

            override fun getItemLayoutId(): Int {
                return R.layout.em_activity_voice_call
            }
        })
    }

    /**
     * set call state listener
     */
    internal fun addCallStateListener() {
        callStateListener = EMCallStateChangeListener { callState, error ->
            // Message msg = handler.obtainMessage();
            EMLog.d("EMCallManager", "onCallStateChanged:$callState")
            when (callState) {

                EMCallStateChangeListener.CallState.CONNECTING -> runOnUiThread { callStateTextView.text = "连接中" }
                EMCallStateChangeListener.CallState.CONNECTED -> runOnUiThread {
                    val st3 = resources.getString(R.string.have_connected_with)
                    callStateTextView.text = st3
                }

                EMCallStateChangeListener.CallState.ACCEPTED -> {
                    runOnUiThread {
                        try {
                            soundPool?.stop(streamID)
                        } catch (e: Exception) {
                        }

//                        if (!isHandsfreeState)
//                            closeSpeakerOn()
//                        //show relay or direct call, for testing purpose
//                        (findViewById(R.id.tv_is_p2p) as TextView).setText(if (EMClient.getInstance().callManager().isDirectCall)
//                            R.string.direct_call
//                        else
//                            R.string.relay_call)

//                        chronometer.setVisibility(View.VISIBLE)
//                        chronometer.setBase(SystemClock.elapsedRealtime())
//                        // duration start
//                        chronometer.start()
                        val str4 = resources.getString(R.string.In_the_call)
                        callStateTextView.text = str4
                        callingState = CallingState.NORMAL
                        startMonitor()
                    }
                }
                EMCallStateChangeListener.CallState.NETWORK_UNSTABLE -> runOnUiThread {
                    netwrokStatusVeiw.setVisibility(View.VISIBLE)
                    if (error == EMCallStateChangeListener.CallError.ERROR_NO_DATA) {
                        netwrokStatusVeiw.setText(R.string.no_call_data)
                    } else {
                        netwrokStatusVeiw.setText(R.string.network_unstable)
                    }
                }
                EMCallStateChangeListener.CallState.NETWORK_NORMAL -> runOnUiThread { netwrokStatusVeiw.setVisibility(View.INVISIBLE) }
                EMCallStateChangeListener.CallState.VOICE_PAUSE -> runOnUiThread { T_.info("VOICE_PAUSE") }
                EMCallStateChangeListener.CallState.VOICE_RESUME -> runOnUiThread { T_.info("VOICE_RESUME") }
                EMCallStateChangeListener.CallState.DISCONNECTED -> {
                    runOnUiThread(object : Runnable {
                        private fun postDelayedCloseMsg() {
//                            handler.postDelayed(Runnable {
//                                runOnUiThread {
//                                    Log.d("AAA", "CALL DISCONNETED")
//                                    removeCallStateListener()
//                                    saveCallRecord()
//                                    val animation = AlphaAnimation(1.0f, 0.0f)
//                                    animation.duration = 800
//                                    findViewById(R.id.root_layout).startAnimation(animation)
//                                    finish()
//                                }
//                            }, 200)
                        }

                        override fun run() {
//                            chronometer.stop()
//                            callDruationText = chronometer.getText().toString()
                            callDruationText = "00:00:60"
                            val st1 = resources.getString(R.string.Refused)
                            val st2 = resources.getString(R.string.The_other_party_refused_to_accept)
                            val st3 = resources.getString(R.string.Connection_failure)
                            val st4 = resources.getString(R.string.The_other_party_is_not_online)
                            val st5 = resources.getString(R.string.The_other_is_on_the_phone_please)

                            val st6 = resources.getString(R.string.The_other_party_did_not_answer_new)
                            val st7 = resources.getString(R.string.hang_up)
                            val st8 = resources.getString(R.string.The_other_is_hang_up)

                            val st9 = resources.getString(R.string.did_not_answer)
                            val st10 = resources.getString(R.string.Has_been_cancelled)
                            val st11 = resources.getString(R.string.hang_up)

                            if (error == EMCallStateChangeListener.CallError.REJECTED) {
                                callingState = CallingState.BEREFUSED
                                callStateTextView.setText(st2)
                            } else if (error == EMCallStateChangeListener.CallError.ERROR_TRANSPORT) {
                                callStateTextView.setText(st3)
                            } else if (error == EMCallStateChangeListener.CallError.ERROR_UNAVAILABLE) {
                                callingState = CallingState.OFFLINE
                                callStateTextView.setText(st4)
                            } else if (error == EMCallStateChangeListener.CallError.ERROR_BUSY) {
                                callingState = CallingState.BUSY
                                callStateTextView.setText(st5)
                            } else if (error == EMCallStateChangeListener.CallError.ERROR_NORESPONSE) {
                                callingState = CallingState.NO_RESPONSE
                                callStateTextView.setText(st6)
                            } else if (error == EMCallStateChangeListener.CallError.ERROR_LOCAL_SDK_VERSION_OUTDATED || error == EMCallStateChangeListener.CallError.ERROR_REMOTE_SDK_VERSION_OUTDATED) {
                                callingState = CallingState.VERSION_NOT_SAME
                                callStateTextView.setText(R.string.call_version_inconsistent)
                            } else {
//                                if (isRefused) {
//                                    callingState = CallingState.REFUSED
//                                    callStateTextView.setText(st1)
//                                } else if (isAnswered) {
//                                    callingState = CallingState.NORMAL
//                                    if (endCallTriggerByMe) {
//                                        //                                        callStateTextView.setText(st7);
//                                    } else {
//                                        callStateTextView.setText(st8)
//                                    }
//                                } else {
//                                    if (isInComingCall) {
//                                        callingState = CallingState.UNANSWERED
//                                        callStateTextView.setText(st9)
//                                    } else {
//                                        if (callingState !== CallingState.NORMAL) {
//                                            callingState = CallingState.CANCELLED
//                                            callStateTextView.setText(st10)
//                                        } else {
//                                            callStateTextView.setText(st11)
//                                        }
//                                    }
//                                }
                            }
                            postDelayedCloseMsg()
                        }

                    })
                }

                else -> {
                }
            }
        }
        EMClient.getInstance().callManager().addCallStateChangeListener(callStateListener)
    }

    fun removeCallStateListener() {
        EMClient.getInstance().callManager().removeCallStateChangeListener(callStateListener)
    }

    private var monitor = false

    /**
     * for debug & testing, you can remove this when release
     */
    internal fun startMonitor() {
        monitor = true
        Thread(Runnable {
            runOnUiThread {
                tvIsP2p.setText(if (EMClient.getInstance().callManager().isDirectCall)
                    R.string.direct_call
                else
                    R.string.relay_call)
            }
            while (monitor) {
                try {
                    Thread.sleep(1500)
                } catch (e: InterruptedException) {
                }

            }
        }, "CallMonitor").start()
    }

    internal fun stopMonitor() {

    }
}