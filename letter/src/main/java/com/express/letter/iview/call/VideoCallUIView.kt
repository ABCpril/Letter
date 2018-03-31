package com.express.letter.iview.call

import android.widget.ImageView
import android.widget.TextView
import com.angcyo.uiview.base.Item
import com.angcyo.uiview.base.SingleItem
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.hyphenate.chat.EMClient
import com.hyphenate.exceptions.HyphenateException

/**
 * 视频通话
 * Created by angcyo on 2018/03/31 09:04
 */
class VideoCallUIView(username: String /*用户*/, isCallTo: Boolean = true /*主叫方*/) : BaseCallUIView(username, isCallTo) {

    init {
        callType = 1
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
                return R.layout.em_activity_video_call
            }
        })
    }

}