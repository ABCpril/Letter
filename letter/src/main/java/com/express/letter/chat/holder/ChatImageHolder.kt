package com.express.letter.chat.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.hyphenate.chat.EMImageMessageBody
import com.hyphenate.chat.EMMessage

/**
 * Created by angcyo on 2018/03/24 11:17
 */
class ChatImageHolder : BaseChatHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EMMessage) {
        super.onBindItemDataView(holder, posInData, dataBean)
        (dataBean.body as EMImageMessageBody?)?.let { body ->
            holder.glideImgV(R.id.image_view).apply {
                reset()
                placeholderRes = R.drawable.ease_default_image
            }

//            holder.tv(R.id.file_name_view).text = body.fileName
//            holder.tv(R.id.file_size_view).text = RUtils.formatFileSize(body.fileSize)
//
//            holder.click(messageContentControlLayout) {
//                val file = File(body.localUrl)
//                if (file.exists()) {
//                    RUtils.openFile(exItemUIView!!.mActivity, file)
//                } else {
//                    T_.error("请等待文件下载完成.")
//                }
//            }
        }
    }

    override fun getMessageContentLayoutId(): Int {
        return R.layout.message_image_content_layout
    }
}