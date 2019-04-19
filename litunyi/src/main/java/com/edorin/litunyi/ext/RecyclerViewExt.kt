package com.edorin.litunyi.ext

import androidx.recyclerview.widget.RecyclerView

/**
 * Setup [RecyclerView]
 * @param recyclerViewLayoutManager [RecyclerView.LayoutManager]
 * @param recyclerViewAdapter [RecyclerView.Adapter]
 */
fun RecyclerView.setup(
    recyclerViewLayoutManager: RecyclerView.LayoutManager,
    recyclerViewAdapter: RecyclerView.Adapter<*>?) {
    layoutManager = recyclerViewLayoutManager
    adapter = recyclerViewAdapter
}
