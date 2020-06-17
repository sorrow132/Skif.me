package yuresko.skifme.adapter

import androidx.recyclerview.widget.DiffUtil
import yuresko.skifme.repository.Item

class DiffUtilCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem is Item && newItem is Item
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}