package com.rsdosev.rimemployeesluas.tramstopforecast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rsdosev.domain.model.Tram
import com.rsdosev.rimemployeesluas.R
import kotlinx.android.synthetic.main.list_item_tram.view.*

class TramsAdapter : ListAdapter<Tram, TramViewHolder>(TramItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TramViewHolder(
        itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item_tram, parent, false
        )
    )

    override fun onBindViewHolder(holder: TramViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class TramViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Tram?) {
        item?.run {
            itemView.destinationView.text = item.destination
            itemView.dueMinsView.text = "in ${item.dueMinutes.toIntOrNull() ?: 0} minutes"

        }
    }
}

class TramItemDiffCallback : DiffUtil.ItemCallback<Tram>() {

    override fun areItemsTheSame(
        oldItem: Tram,
        newItem: Tram
    ) = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: Tram,
        newItem: Tram
    ) = oldItem == newItem
}
