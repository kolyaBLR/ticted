package com.example.ticder.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.ticder.model.SwipeInfo
import com.example.ticder.viewmodel.IImageViewModel

class SwipeHelper(private val viewModel: IImageViewModel) : ItemTouchHelper.Callback() {

    var swipeEnabled = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(0, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean = swipeEnabled

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        val like = direction == ItemTouchHelper.END
        viewModel.onSwipeItem(SwipeInfo(position, like))
    }
}
