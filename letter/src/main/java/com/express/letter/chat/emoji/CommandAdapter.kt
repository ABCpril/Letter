package com.express.letter.chat.emoji

import android.content.Context
import com.angcyo.uiview.recycler.RBaseViewHolder
import com.angcyo.uiview.recycler.adapter.RExBaseAdapter
import com.express.letter.R

/**
 * Created by angcyo on 2018/03/25 19:29
 */
class CommandAdapter(context: Context, items: List<CommandItem>) : RExBaseAdapter<String, CommandItem, String>(context, items) {
    override fun getItemLayoutId(viewType: Int): Int {
        return R.layout.item_command_layout
    }

    override fun onBindDataView(holder: RBaseViewHolder, posInData: Int, dataBean: CommandItem?) {
        super.onBindDataView(holder, posInData, dataBean)
        dataBean?.let {
            holder.rtv(R.id.text_view).apply {
                setTopIco(it.icoId)
                text = it.text
            }
            holder.clickItem(it.onItemClick)
        }

    }
}