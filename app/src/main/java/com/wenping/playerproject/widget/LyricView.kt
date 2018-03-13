package com.wenping.playerproject.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.wenping.playerproject.R
import com.wenping.playerproject.model.LyricBean
import com.wenping.playerproject.util.LyricLoader
import com.wenping.playerproject.util.LyricUtil
import kotlinx.android.synthetic.main.item_vbang.view.*
import org.jetbrains.anko.doAsync


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
    var centerLine = 0
    var lineHeight = 0

    var duration = 0
    var progress = 0
    var updateByPro = true//指定是否可以通过progress进度来更新歌词;默认为true

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
        for (i in 0 until 100) {
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
        if (list.size == 0) {
            drawSingleLine(canvas)
        } else {
            drawMultiLines(canvas)
        }

//        drawSingleLine(canvas)
    }

    var offsetY = 0f;
    private fun drawMultiLines(canvas: Canvas?) {

        if (updateByPro) {
            //行可用时间
            var lineTime = 0
            //最后一行居中
            if (centerLine == list.size - 1) {
                //行可用时间 =
                lineTime = duration - list.get(centerLine).startTime
            } else {
                //其他行居中
                val centenrS = list.get(centerLine).startTime
                val nextS = list.get(centerLine + 1).startTime
                lineTime = nextS - centenrS
            }
            //偏移时间 = pgogress - 居中行开始时间
            val offsetTime = progress - list.get(centerLine).startTime
            //偏移的百分比 = 偏移时间/行可用时间
            val offsetPercent = offsetTime / (lineTime.toFloat())
            //偏移y = 偏移百分比*行高
            val offsetY = offsetPercent * lineHeight
        }

        val centerText = list.get(centerLine).content

        val bounds = Rect()
        paint.getTextBounds(centerText, 0, centerText.length, bounds)
        val textH = bounds.height()
        //居中行y
        val centerY = viewH / 2 + textH / 2 - offsetY
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
            val curY = centerY + (index - centerLine) * lineHeight

            /**
             * 处理超出边界的绘画
             */
            if (curY < 0) continue
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

        val text = "正在加载歌词--"
        val bounds = Rect()
        paint.getTextBounds(text, 0, text.length, bounds)
        val textW = bounds.width()
        val textH = bounds.height()
        val y = viewH / 2 + textH / 2

        //绘制内容
        canvas?.drawText(text, viewW / 2.toFloat(), y.toFloat(), paint)
    }

    /**
     * 传递当前播放进度,实现歌词播放
     */
    fun updateProgress(progress: Int) {
        if (!updateByPro) return
        if (list.size == 0) return

        this.progress = progress
        //获取居中行号
        //先判断居中行是否是最后一行
        if (progress >= (list.get(list.size - 1)).startTime) {
            //最后一行居中
            centerLine = list.size - 1
        } else {
            //其他居中 循环遍历集合;因为list.size-1已经拿到了,所以现在取不到list.size-1
            for (index in 0 until list.size - 1) {
                //progress>=当前开始时间&&progress<下一开始时间
                val curStartTime = list.get(index).startTime
                val nextStartTime = list.get(index + 1).startTime
                if (progress > curStartTime && progress < nextStartTime) {
                    centerLine = index
                    break
                }
            }
        }
        invalidate()//onDraw方法
//        postInvalidate()//onDraw方法可以在子线程中刷新
        requestLayout()//view布局参数改变时刷新
    }

    /**
     * 设置当前 播放歌曲总时长
     *
     */
    fun setSongDuration(duration: Int) {
        this.duration = duration
    }

    fun setSongName(name: String) {

        doAsync {
            this@LyricView.list.clear()
            this@LyricView.list.addAll(LyricUtil.parseLyric(LyricLoader.loadLyricFile(name)))
        }
    }

    var downY = 0f
    var markY  = 0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    //停止通过进度更新歌词
                    updateByPro = false
                    downY = event.y
                    //记录原来进度已经更新的y
                    markY= this.offsetY
                }
                MotionEvent.ACTION_UP -> {
                    updateByPro = true
                }
                MotionEvent.ACTION_MOVE ->{
                    val endY = event.y
                    val offY = downY-endY
                    //重新设置居中行
                    this.offsetY = offY+markY
                    //如果最终的y的偏移大于行高,需要重新确定居中行
                    if (Math.abs(this.offsetY) >= lineHeight) {
                        //求居中行行号偏移
                        val offsetLine = (this.offsetY/lineHeight).toInt()
                        centerLine+=offsetLine

                        if (centerLine<0)centerLine = 0 else if (centerLine> list.size-1) centerLine = list.size - 1
                        //downY重新设置
                        this.downY = endY
                        //重新确定偏移
                        this.offsetY = this.offsetY % lineHeight
                        markY = this.offsetY
                    }
                    //重新绘制
                    invalidate()
                }
            }

        }

        //消耗这个事件
        return true
    }
}