package com.wzq.mvvmsmart.binding.viewadapter.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.wzq.mvvmsmart.binding.viewadapter.recyclerview.DividerLine.LineDrawMode

object LineManagers {
    fun both(): LineManagerFactory {
        return object : LineManagerFactory {
            override fun create(recyclerView: RecyclerView): ItemDecoration {
                return DividerLine(recyclerView.context, LineDrawMode.BOTH)
            }
        }
    }

    fun horizontal(): LineManagerFactory {
        return object : LineManagerFactory {
            override fun create(recyclerView: RecyclerView): ItemDecoration {
                return DividerLine(recyclerView.context, LineDrawMode.HORIZONTAL)
            }
        }
    }

    fun vertical(): LineManagerFactory {
        return object : LineManagerFactory {
            override fun create(recyclerView: RecyclerView): ItemDecoration {
                return DividerLine(recyclerView.context, LineDrawMode.VERTICAL)
            }
        }
    }

    interface LineManagerFactory {
        fun create(recyclerView: RecyclerView): ItemDecoration
    }
}