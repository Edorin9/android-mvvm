package com.edorin.mvvmtemplate.view.colors.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edorin.litunyi.ext.inflate
import com.edorin.mvvmtemplate.R
import com.edorin.mvvmtemplate.domain.entity.Color
import kotlinx.android.synthetic.main.item_color.view.*

class ColorsAdapter(
    private val colors: List<Color>,
    private val itemClickHandler: ((Int?) -> Unit)
) : RecyclerView.Adapter<ColorsAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(parent.inflate(R.layout.item_color))

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(colors[position])

    override fun getItemCount() = colors.size

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(color: Color) {
            itemView.apply {
                color_text_name.apply {
                    text = color.name
                    setTextColor(color.value)
                }
                setOnClickListener { itemClickHandler(color.id) }
            }
        }
    }

}