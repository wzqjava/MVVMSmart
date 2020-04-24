package com.wzq.mvvmsmart.binding.viewadapter.recyclerview

import android.R
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class DividerLine(private val mContext: Context) : ItemDecoration() {
    //divider对应的drawable
    private val dividerDrawable: Drawable?
    var dividerSize = 0

    //默认为null
    var mode: LineDrawMode? = null

    /**
     * 分隔线绘制模式,水平，垂直，两者都绘制
     */
    enum class LineDrawMode {
        HORIZONTAL, VERTICAL, BOTH
    }

    constructor(context: Context, mode: LineDrawMode?) : this(context) {
        this.mode = mode
    }

    constructor(context: Context, dividerSize: Int, mode: LineDrawMode?) : this(context, mode) {
        this.dividerSize = dividerSize
    }

    /**
     * Item绘制完毕之后绘制分隔线
     * 根据不同的模式绘制不同的分隔线
     *
     * @param c
     * @param parent
     * @param state
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        checkNotNull(mode) { "assign LineDrawMode,please!" }
        when (mode) {
            LineDrawMode.VERTICAL -> drawVertical(c, parent, state)
            LineDrawMode.HORIZONTAL -> drawHorizontal(c, parent, state)
            LineDrawMode.BOTH -> {
                drawHorizontal(c, parent, state)
                drawVertical(c, parent, state)
            }
        }
    }

    /**
     * 绘制垂直分隔线
     *
     * @param c
     * @param parent
     * @param state
     */
    private fun drawVertical(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            val top = child.top - params.topMargin
            val bottom = child.bottom + params.bottomMargin
            val left = child.right + params.rightMargin
            val right = if (dividerSize == 0) left + dip2px(mContext, DEFAULT_DIVIDER_SIZE.toFloat()) else left + dividerSize
            dividerDrawable!!.setBounds(left, top, right, bottom)
            dividerDrawable.draw(c)
        }
    }

    /**
     * 绘制水平分隔线
     *
     * @param c
     * @param parent
     * @param state
     */
    private fun drawHorizontal(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            //分别为每个item绘制分隔线,首先要计算出item的边缘在哪里,给分隔线定位,定界
            val child = parent.getChildAt(i)
            //RecyclerView的LayoutManager继承自ViewGroup,支持了margin
            val params = child.layoutParams as RecyclerView.LayoutParams
            //child的左边缘(也是分隔线的左边)
            val left = child.left - params.leftMargin
            //child的底边缘(恰好是分隔线的顶边)
            val top = child.bottom + params.topMargin
            //child的右边(也是分隔线的右边)
            val right = child.right - params.rightMargin
            //分隔线的底边所在的位置(那就是分隔线的顶边加上分隔线的高度)
            val bottom = if (dividerSize == 0) top + dip2px(mContext, DEFAULT_DIVIDER_SIZE.toFloat()) else top + dividerSize
            dividerDrawable!!.setBounds(left, top, right, bottom)
            //画上去
            dividerDrawable.draw(c)
        }
    }

    companion object {
        private val TAG = DividerLine::class.java.canonicalName

        //默认分隔线厚度为2dp
        private const val DEFAULT_DIVIDER_SIZE = 1

        //控制分隔线的属性,值为一个drawable
        private val ATTRS = intArrayOf(R.attr.listDivider)

        /**
         * 将dip或dp值转换为px值，保证尺寸大小不变
         *
         * @param dipValue
         * @param context（DisplayMetrics类中属性density）
         * @return
         */
        fun dip2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }
    }

    init {
        //获取样式中对应的属性值
        val attrArray = mContext.obtainStyledAttributes(ATTRS)
        dividerDrawable = attrArray.getDrawable(0)
        attrArray.recycle()
    }
}