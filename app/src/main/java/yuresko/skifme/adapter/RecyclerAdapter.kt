package yuresko.skifme.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import yuresko.skifme.registration.model.Item
import java.util.*

class RecyclerAdapter : ListAdapter<Item, RecyclerView.ViewHolder>(DiffUtilCallback()),
    ItemTouchHelperAdapter {

    val listOfItems: MutableList<Item> =
        mutableListOf(
            Item(
                1,
                "+7(999)999-99-99",
                "14:00"
            ),
            Item(
                2,
                "+7(999)999-99-99",
                "14:00"
            ),
            Item(
                3,
                "+7(999)999-99-99",
                "14:00"
            ),
            Item(
                4,
                "+7(999)999-99-99",
                "14:00"
            ),
            Item(
                5,
                "+7(999)999-99-99",
                "14:00"
            )
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CustomViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CustomViewHolder) {
            holder.bind(getItem(position))
        }
    }

    fun onRowMoved(fromPosition: Int, toPosition: Int): MutableList<Item> {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listOfItems, i, i + 1)

            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listOfItems, i, i - 1)
            }
        }
        listOfItems[fromPosition].id = fromPosition
        listOfItems[toPosition].id = toPosition
        notifyItemMoved(fromPosition, toPosition)
        return listOfItems
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listOfItems, i, i + 1)

            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listOfItems, i, i - 1)
            }
        }
        listOfItems[fromPosition].id = fromPosition
        listOfItems[toPosition].id = toPosition
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        listOfItems.removeAt(position)
        notifyItemRemoved(position)
    }
}