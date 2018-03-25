package com.express.letter.chat.emoji

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ImageSpan
import android.util.AttributeSet
import android.widget.EditText
import com.angcyo.uiview.widget.RExTextView

/**
 * Created by angcyo on 2018/03/25 13:56
 */
class EmojiTextView(context: Context, attributeSet: AttributeSet? = null) : RExTextView(context, attributeSet) {

    companion object {
        val emojiSize = 45

        fun getDrawable(context: Context, icoRes: Int): Drawable {
            return ContextCompat.getDrawable(context, icoRes)!!
        }

        fun showEmoji(editText: EditText) {
            val text = editText.text
            val spanBuilder = SpannableStringBuilder(text)

            val selectionStart = editText.selectionStart

            for (entry in EaseSmileUtils.emojisPattern) {
                val matcher = entry.value.matcher(text)

                while (matcher.find()) {
                    val start = matcher.start()
                    val end = matcher.end()
                    val group = matcher.group()

                    val drawable = getDrawable(editText.context, EaseSmileUtils.emojisRes[entry.key]!!)
                    drawable.setBounds(0, 0, emojiSize, emojiSize)

                    spanBuilder.setSpan(ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM),
                            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

//                    spanBuilder.setSpan(ImageSpan(editText.context, EaseSmileUtils.emojisRes[entry.key]!!, ImageSpan.ALIGN_BOTTOM),
//                            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

            editText.text = spanBuilder

            if (selectionStart >= 0) {
                editText.setSelection(selectionStart)
            }
        }
    }

    override fun afterPattern(spanBuilder: SpannableStringBuilder, text: CharSequence) {
        super.afterPattern(spanBuilder, text)

        for (entry in EaseSmileUtils.emojisPattern) {
            val matcher = entry.value.matcher(text)

            while (matcher.find()) {
                val start = matcher.start()
                val end = matcher.end()
                val group = matcher.group()

                if (!isInOtherSpan(spanBuilder, text.length, start, end)) {

                    val drawable = getDrawable(context, EaseSmileUtils.emojisRes[entry.key]!!)
                    drawable.setBounds(0, 0, emojiSize, emojiSize)

                    spanBuilder.setSpan(ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM),
                            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

//                    spanBuilder.setSpan(ImageSpan(context, EaseSmileUtils.emojisRes[entry.key]!!, ImageSpan.ALIGN_BOTTOM),
//                            start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }
        }
    }
}