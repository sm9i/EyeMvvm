package com.sm9i.eyemvvm.widget.font

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView


/**
 * 自定义文字
 */
open class CustomFontTextView : AppCompatTextView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        ///是否是编辑模式
        if (!isInEditMode) {
            TypefaceManager.setTextTypeFace(context, attrs, this)
        }
    }

    /**
     * 设置字体类型
     */
    fun setFontType(fontType: FontType) {
        if (!isInEditMode) {
            TypefaceManager.setTextTypeFace(this, fontType)
        }
    }

    /**
     * 根据字体设置名称 没找到就默认字体
     */
    fun setFontType(fontName: String?, defaultFontType: FontType = FontType.BOLD) {
        if (!isInEditMode) {
            val fontType = TypefaceManager.getFontTypeByName(fontName)
            TypefaceManager.setTextTypeFace(this, fontType ?: defaultFontType)
        }
    }
}