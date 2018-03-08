package com.globallogic.dashpoc.dashboard

import android.support.v7.widget.RecyclerView
import android.view.View
import com.globallogic.dashpoc.R
import kotlinx.android.synthetic.main.item_dashboard.view.*

/**
 * Created by snowcat on 8.3.2018.
 */
class DashboardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(type: DashItemType) {
        itemView.iv_icon.setImageResource(R.drawable.ic_fuel_counter)
        itemView.tv_item_name.text = itemView.resources.getString(R.string.consumption)
    }
}