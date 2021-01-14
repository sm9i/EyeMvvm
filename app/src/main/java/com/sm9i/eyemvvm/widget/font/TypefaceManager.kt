package com.sm9i.eyemvvm.widget.font

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.sm9i.base.application.BaseApplication
import com.sm9i.eyemvvm.R
import java.util.jar.Attributes


/**
 * 字体管理工具
 */
object TypefaceManager {


    private val mTypeFaceMap: MutableMap<FontType, Typeface> = mutableMapOf()

    private var mTypeFaceIndex: Int = FontType.NORMAL.index


    /**
     * 设置textView字体，
     * 如果参数有字体就用本身
     * 没有就用设置的
     */
    fun setTextTypeFace(context: Context, attributes: AttributeSet?, textView: TextView) {
        if (textView.typeface != null && textView.typeface.style != 0) {
            return
        }
        val typeArray = context.obtainStyledAttributes(attributes, R.styleable.CustomFontTextView)
        mTypeFaceIndex = typeArray.getInteger(
            R.styleable.CustomFontTextView_font_name,
            mTypeFaceIndex
        )
        if (mTypeFaceIndex in 0..FontType.values().size) {
            textView.typeface = getTypeFace(FontType.values()[mTypeFaceIndex])
        }
        typeArray.recycle()
    }

    /**
     * 根据字体设置textView显示的字体
     */
    fun setTextTypeFace(textView: TextView, fontType: FontType?) {
        textView.typeface = getTypeFace(fontType)
    }

    /**
     * 根据字体类型获取字体
     */
    fun getFontTypeByName(fontName: String?): FontType? {
        return FontType.values().firstOrNull {
            it.fontName === fontName
        }
    }

    /**
     * 根据字体类型获取字体
     */
    fun getTypeFace(fontType: FontType?): Typeface? {
        return fontType?.let {
            var typeface = mTypeFaceMap[it]
            if (typeface == null) {
                typeface = Typeface.createFromAsset(BaseApplication.INSTANCE.assets, fontType.path)
                mTypeFaceMap[it] = typeface
            }
            return typeface
        }

    }


}