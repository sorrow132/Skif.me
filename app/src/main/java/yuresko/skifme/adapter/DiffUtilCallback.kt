package yuresko.skifme.adapter

import androidx.recyclerview.widget.DiffUtil
import yuresko.skifme.mainmenu.model.PhoneTime

class DiffUtilCallback : DiffUtil.ItemCallback<PhoneTime>() {
    override fun areItemsTheSame(oldPhoneTime: PhoneTime, newPhoneTime: PhoneTime): Boolean {
        return oldPhoneTime is PhoneTime && newPhoneTime is PhoneTime
    }

    override fun areContentsTheSame(oldPhoneTime: PhoneTime, newPhoneTime: PhoneTime): Boolean {
        return oldPhoneTime == newPhoneTime
    }
}