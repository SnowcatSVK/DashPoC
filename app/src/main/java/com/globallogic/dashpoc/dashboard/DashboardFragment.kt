package com.globallogic.dashpoc.dashboard


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.globallogic.dashpoc.R
import kotlinx.android.synthetic.main.fragment_dashboard.*


/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dAdapter = DashboardAdapter()
        val items = arrayListOf<DashItemType>()
        items.add(DashItemType.TYPE_CONSUMPTION)
        rv_dash.apply {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
            adapter = dAdapter
        }
        dAdapter.setData(items)
    }
}
