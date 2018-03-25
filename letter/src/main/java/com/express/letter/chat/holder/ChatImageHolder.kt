package com.express.letter.chat.holder

import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.UI
import com.express.letter.R
import com.hyphenate.chat.EMImageMessageBody
import com.hyphenate.chat.EMMessage
import java.io.File

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

                val thumbnailLocalPath = body.thumbnailLocalPath()
                val localThumbFile = File(thumbnailLocalPath)
                var width = body.width
                var height = body.height
                if (localThumbFile.exists()) {
                    url = thumbnailLocalPath
                    if (width == 0 || height == 0) {
                        val imageSize = RUtils.getImageSize(url)
                        width = imageSize[0]
                        height = imageSize[1]
                    }
                } else {
                    url = body.thumbnailUrl
                }

                //L.e("ChatImageHolder tlp:$url w:${width} h:${height}")
                UI.setView(this, width, height)
            }

//            holder.tv(R.id.file_name_view).text = body.fileName
//            holder.tv(R.id.file_size_view).text = RUtils.formatFileSize(body.fileSize)
//
            holder.click(messageContentControlLayout) {

            }
        }
    }

    override fun getMessageContentLayoutId(): Int {
        return R.layout.message_image_content_layout
    }
}