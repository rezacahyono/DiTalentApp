package com.capstone.ditalent.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Px
import com.capstone.ditalent.R
import com.google.android.material.imageview.ShapeableImageView
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AvatarView : ShapeableImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    private val textPaint = Paint().apply {
        textAlign = Paint.Align.CENTER
        style = Paint.Style.FILL
    }

    private val backgroundPaint = Paint().apply { style = Paint.Style.FILL }

    var avatarInitials: String? by viewProperty(null)

    var avatarCircle: Boolean by viewProperty(false)

    @get:ColorInt
    var avatarInitialsBackgroundColor: Int by viewProperty(Color.parseColor("#FFC16E"))

    @get:ColorInt
    var avatarInitialsTextColor: Int by viewProperty(Color.WHITE)

    var avatarInitialsStyle: Int by viewProperty(Typeface.NORMAL)

    @get:Px
    var avatarInitialsTextSize: Int by viewProperty(-1)

    @get:FloatRange(from = 0.0, to = 1.0)
    var avatarInitialsTextSizeRatio: Float by viewProperty(0.33f)

    override fun onDraw(canvas: Canvas) {
        if (avatarInitials.isNullOrEmpty()) {
            super.onDraw(canvas)
        } else {
            applyPaintStyles()
            drawInitials(canvas)

        }
    }

    private fun applyPaintStyles() {
        backgroundPaint.color = avatarInitialsBackgroundColor
        textPaint.color = avatarInitialsTextColor
        textPaint.typeface = Typeface.defaultFromStyle(avatarInitialsStyle)
        textPaint.textSize = avatarInitialsTextSize.takeIf { it != -1 }?.toFloat()
            ?: (avatarInitialsTextSizeRatio * width)
    }

    private fun drawInitials(canvas: Canvas) {
        val initials = avatarInitials ?: return
        if (avatarCircle){
            canvas.drawCircle(
                width / 2f,
                height / 2f,
                width / 2f,
                backgroundPaint
            )
        }else {
            canvas.drawRoundRect(
                0F,
                0F,
                width.toFloat(),
                height.toFloat(),
                0F,
                0F,
                backgroundPaint
            )
        }
        canvas.drawText(
            initials.parseInitials,
            width.toFloat() / 2,
            (canvas.height / 2) - ((textPaint.descent() + textPaint.ascent()) / 2),
            textPaint
        )
    }

    private val String.parseInitials: String
        @JvmSynthetic inline get() {
            val textList = trim().split(" ")
            return when {
                textList.size > 1 -> "${textList[0][0]}${textList[1][0]}"
                textList[0].length > 1 -> "${textList[0][0]}${textList[0][1]}"
                textList[0].isNotEmpty() -> "${textList[0][0]}"
                else -> ""
            }.uppercase()
        }
}

@JvmSynthetic
internal fun <T : Any?> View.viewProperty(defaultValue: T): ViewPropertyDelegate<T> {
    return ViewPropertyDelegate(defaultValue) {
        invalidate()
    }
}

internal class ViewPropertyDelegate<T : Any?>(
    defaultValue: T,
    private val invalidator: () -> Unit
) : ReadWriteProperty<Any?, T> {

    private var propertyValue: T = defaultValue

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return propertyValue
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (propertyValue != value) {
            propertyValue = value
            invalidator()
        }
    }
}
