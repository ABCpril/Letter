package com.express.letter.chat.holder

import com.angcyo.uiview.base.UIVideoView
import com.angcyo.uiview.kotlin.isFileExists
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.utils.RUtils
import com.angcyo.uiview.utils.UI
import com.express.letter.R
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMVideoMessageBody

/**
 * Created by angcyo on 2018/03/24 11:17
 */
class ChatVideoHolder : BaseChatHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EMMessage) {
        super.onBindItemDataView(holder, posInData, dataBean)
        (dataBean.body as EMVideoMessageBody?)?.let { body ->
            holder.gIV(R.id.image_view).apply {
                reset()

                var width = body.thumbnailWidth
                var height = body.thumbnailHeight

                if (body.localThumb.isFileExists()) {
                    url = body.localThumb

                    if (width == 0 || height == 0) {
                        val imageSize = RUtils.getImageSize(url)
                        width = imageSize[0]
                        height = imageSize[1]
                    }
                } else {
                    url = body.thumbnailUrl
                }

                UI.setView(this, width, height)
            }

            holder.click(messageContentControlLayout) {
                startIView(UIVideoView(body.localUrl).apply {
                    autoPlay = true
                })
            }
        }
    }

    override fun getMessageContentLayoutId(): Int {
        return R.layout.message_video_content_layout
    }
}