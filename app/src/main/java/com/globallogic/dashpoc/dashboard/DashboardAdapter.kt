package com.globallogic.dashpoc.dashboard

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.globallogic.dashpoc.R

/**
 * Created by snowcat on 8.3.2018.
 */
class DashboardAdapter : RecyclerView.Adapter<DashboardViewHolder>() {

    var items: ArrayList<DashItemType> = ArrayList()

    fun setData(data: ArrayList<DashItemType>) {
        items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DashboardViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent?.context)
        val itemView: View = inflater.inflate(R.layout.item_dashboard, parent, false)
        return DashboardViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder?, position: Int) {
        holder?.bindData(items[position])
    }
}