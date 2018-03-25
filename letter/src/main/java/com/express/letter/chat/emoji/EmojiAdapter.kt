package com.express.letter.chat.emoji

import android.content.Context
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.express.letter.R

/**
 * Created by angcyo on 2018/03/25 14:16
 */
class EmojiAdapter(context: Context) : RExBaseAdapter<String, EaseEmojicon, String>(context) {
    init {
        mAllDatas.addAll(EaseDefaultEmojiconDatas.getData())
    }

    var onEmojiClick: ((EaseEmojicon) -> Unit)? = null

    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.item_emoji_layout
    }

    override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: EaseEmojicon?) {
        super.onBindDataView(holder, posInData, dataBean)
        dataBean?.let {
            holder.imageView(R.id.image_view).setImageResource(it.icon)
            holder.clickItem {
                onEmojiClick?.invoke(dataBean)
            }
        }

    }
}