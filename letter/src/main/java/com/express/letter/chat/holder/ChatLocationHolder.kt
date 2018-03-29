package com.express.letter.chat.holder

import com.amap.api.maps.model.LatLng
import com.angcyo.amap.iview.RAmapUIView
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.express.letter.R
import com.hyphenate.chat.EMLocationMessageBody
import com.hyphenate.chat.EMMessage

/**
 * Created by angcyo on 2018/03/24 11:17
 */
class ChatLocationHolder : BaseChatHolder() {
    override fun onBindItemDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EMMessage) {
        super.onBindItemDataView(holder, posInData, dataBean)
        (dataBean.body as EMLocationMessageBody?)?.let { body ->
            holder.tv(R.id.text_view).text = body.address

            holder.click(messageContentControlLayout) {
                startIView(RAmapUIView(LatLng(body.latitude, body.longitude)))
            }
        }
    }

    override fun getMessageContentLayoutId(): Int {
        return R.layout.message_location_content_layout
    }
}