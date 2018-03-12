package com.wenping.playerproject.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.wenping.playerproject.R
import com.wenping.playerproject.model.LyricBean


/**
 * ClassName:LyricView
 * Description:自定义歌词view
 */
class LyricView : View {

    val paint by lazy { Paint(Paint.ANTI_ALIAS_FLAG) }
    val list by lazy { ArrayList<LyricBean>() }

    var viewW: Int = 0
    var viewH: Int = 0

    var bigSize: Float = 0f
    var smallSize: Float = 0f

    var white = 0
    var green = 0
    var centerLine = 8
    var lineHeight = 0
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        paint.textAlign = Paint.Align.CENTER
        bigSize = resources.getDimension(R.dimen.bigSize)
        smallSize = resources.getDimension(R.dimen.smallSize)
        white = ContextCompat.getColor(context, R.color.white)
        green = ContextCompat.getColor(context, R.color.green)

        lineHeight = resources.getDimensionPixelOffset(R.dimen.lineHeight)
        for (i in 0 until 30) {
            list.add(LyricBean(2000 * i, "第${i}行歌词"))
        }

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

    }

    /**
     * layout方法中执行
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewW = w
        viewH = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawMultiLines(canvas)

//        drawSingleLine(canvas)
    }

    private fun drawMultiLines(canvas: Canvas?) {

        val centerText = list.get(centerLine).content

        val bounds = Rect()
        paint.getTextBounds(centerText, 0, centerText.length, bounds)
        val textH = bounds.height()
        //居中行y
        val centerY = viewH / 2 + textH / 2
        for ((index, value) in list.withIndex()) {

            if (index == centerLine) {
                paint.color = green
                paint.textSize = bigSize
            } else {
                //其他行
                paint.color = white
                paint.textSize = smallSize
            }
            /**
             * curX 是当前宽度的一半 和alignxx并用的
             * curY 是当前行对应centerline的高度 ;行和行之间相差lineheight
             */
            val curX = viewW / 2
            val curY = centerY + (index-centerLine) * lineHeight

            /**
             * 处理超出边界的绘画
             */
            if(curY<0) continue
            /**
             * 下边界超出界面的处理
             */
            if (curY > viewH + lineHeight) break

            val curText = list.get(index).content
            canvas?.drawText(curText, curX.toFloat(), curY.toFloat(), paint)
        }
    }

    /**
     * 画单行线
     */
    private fun drawSingleLine(canvas: Canvas?) {
        paint.textSize = bigSize
        paint.color = green

        val text = "正在加载歌词--Wei"
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textW = bounds.width()
        val textH = bounds.height()
        val y = viewH / 2 + textH / 2

        //绘制内容
        canvas?.drawText(text, viewW / 2.toFloat(), y.toFloat(), paint)
    }
}