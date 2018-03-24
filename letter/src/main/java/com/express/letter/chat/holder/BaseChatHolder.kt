package com.express.letter.chat.holder

import android.view.LayoutInflater
import android.widget.FrameLayout
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.viewgroup.RLinearLayout
import com.express.letter.BuildConfig
import com.express.letter.R
import com.express.letter.chat.ChatUIView
import com.express.letter.iview.BaseItemHolder
import com.hyphenate.chat.EMConversation
import com.hyphenate.chat.EMMessage

/**
 * Created by angcyo on 2018/03/24 10:15
 */
open class BaseChatHolder : BaseItemHolder<EMMessage>() {

    lateinit var messageContentControlLayout: FrameLayout
    lateinit var messageContentRootLayout: RLinearLayout
    var chatUIView: ChatUIView? = null

    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EMMessage) {
        super.onBindItemDataView(holder, posInData, dataBean)
        //头像,昵称, 内容根布局
        messageContentRootLayout = holder.v(R.id.message_content_root_layout)
        //内容包裹布局
        messageContentControlLayout = holder.v(R.id.message_content_control_layout)

        //消息时间
        holder.timeV(R.id.message_time_view).time = dataBean.msgTime
        if (exItemAdapter!!.isDataEqualPrev(posInData, dataBean) { t1, t2 -> t2!!.msgTime - t1!!.msgTime <= 60 * 1000 }) {
            holder.gone(R.id.message_time_view)
        } else {
            holder.visible(R.id.message_time_view)
        }

        //左右气泡切换
        if (/*posInData % 2 == 0 */dataBean.direct() == EMMessage.Direct.SEND) {
            messageContentControlLayout.setBackgroundResource(R.drawable.ease_chatto_bg_normal)
            messageContentRootLayout.isReverseLayout = true
        } else {
            messageContentControlLayout.setBackgroundResource(R.drawable.ease_chatfrom_bg_normal)
            messageContentRootLayout.isReverseLayout = false
        }

        //用户名显示
        holder.tv(R.id.user_name_view).text = dataBean.userName
        if (BuildConfig.DEBUG) {
        } else {
            chatUIView?.let {
                //单聊隐藏用户名
                if (it.type == EMConversation.EMConversationType.Chat) {
                    holder.gone(R.id.user_name_view)
                } else {
                    holder.visible(R.id.user_name_view)
                }
            }
        }

        //消息状态显示
        when (dataBean.status()) {
            EMMessage.Status.CREATE -> {
                holder.tv(R.id.message_status_tip_view).text = "..."
            }
            EMMessage.Status.SUCCESS -> {
                holder.tv(R.id.message_status_tip_view).text = "ok"
            }
            EMMessage.Status.FAIL -> {
                holder.tv(R.id.message_status_tip_view).text = "fail"
            }
            EMMessage.Status.INPROGRESS -> {
                holder.tv(R.id.message_status_tip_view).text = "..."
            }
        }

        if (messageContentControlLayout.childCount == 0) {
            //还没有填充内容
            val layoutId = getMessageContentLayoutId()
            if (layoutId != -1) {
                LayoutInflater.from(holder.context).inflate(layoutId, messageContentControlLayout)
            }
        }
    }

    open fun getMessageContentLayoutId(): Int {
        return -1
    }
}