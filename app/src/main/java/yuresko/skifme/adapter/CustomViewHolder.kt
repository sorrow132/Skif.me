package yuresko.skifme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import yuresko.skifme.R
import yuresko.skifme.mainmenu.model.PhoneTime

class CustomViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.rv_item, parent, false
        )
    ) {

    private var textViewNum: TextView? = itemView.findViewById(R.id.number)
    private var textViewTime: TextView? = itemView.findViewById(R.id.time)

    fun bind(phoneTime: PhoneTime) {
        textViewNum?.text = phoneTime.num
        textViewTime?.text = phoneTime.time
    }

}