package com.npe.galaxyorganic.ui.fragment.order


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.PaymentModel
import com.npe.galaxyorganic.ui.model.db.database
import kotlinx.android.synthetic.main.fragment_order.view.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class OrderFragment : Fragment() {

    private lateinit var recycler : RecyclerView
    private var listOrder :MutableList<PaymentModel> = mutableListOf()
    private lateinit var adapter : AdapterOrder
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_order, container, false)
        showDataDbSql()
        recycler = v.recycler_order
        recycler.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapter = AdapterOrder(requireContext(), listOrder)
        recycler.adapter = adapter
        return v
    }

    fun showDataDbSql(){
        context?.database?.use {
            val result = select(PaymentModel.TABLE_PAYMENT)
            val data = result.parseList(classParser<PaymentModel>())
            listOrder.addAll(data)
        }
    }
}
