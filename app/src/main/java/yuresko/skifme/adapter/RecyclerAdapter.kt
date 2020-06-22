package yuresko.skifme.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import yuresko.skifme.mainmenu.model.PhoneTime
import java.util.*

class RecyclerAdapter : ListAdapter<PhoneTime, RecyclerView.ViewHolder>(DiffUtilCallback()),
    ItemTouchHelperAdapter {

    val listOfPhoneTimes: MutableList<PhoneTime> =
        mutableListOf(
            PhoneTime(
                1,
                "+7(999)999-99-99",
                "14:00"
            ),
            PhoneTime(
                2,
                "+7(999)999-99-99",
                "14:00"
            ),
            PhoneTime(
                3,
                "+7(999)999-99-99",
                "14:00"
            ),
            PhoneTime(
                4,
                "+7(999)999-99-99",
                "14:00"
            ),
            PhoneTime(
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

    fun onRowMoved(fromPosition: Int, toPosition: Int): MutableList<PhoneTime> {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listOfPhoneTimes, i, i + 1)

            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listOfPhoneTimes, i, i - 1)
            }
        }
        listOfPhoneTimes[fromPosition].id = fromPosition
        listOfPhoneTimes[toPosition].id = toPosition
        notifyItemMoved(fromPosition, toPosition)
        return listOfPhoneTimes
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listOfPhoneTimes, i, i + 1)

            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listOfPhoneTimes, i, i - 1)
            }
        }
        listOfPhoneTimes[fromPosition].id = fromPosition
        listOfPhoneTimes[toPosition].id = toPosition
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        listOfPhoneTimes.removeAt(position)
        notifyItemRemoved(position)
    }
}