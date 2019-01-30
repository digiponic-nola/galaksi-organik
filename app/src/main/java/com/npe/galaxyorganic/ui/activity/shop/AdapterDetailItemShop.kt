package com.npe.galaxyorganic.ui.activity.shop

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.db.OrderModel
import kotlinx.android.synthetic.main.list_order_barang.view.*

class AdapterDetailItemShop(val context : Context, val items : List<OrderModel>) :
    RecyclerView.Adapter<AdapterDetailItemShop.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_order_barang,p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val list = items.get(p1)

//        Glide.with(context)
//            .load(list.)

        p0.nama.text = list.product_name
        p0.harga.text = list.product_price.toString()
        p0.jumlah.text = list.quantity.toString()
    }

    class ViewHolder(view : View) :RecyclerView.ViewHolder(view){
        val image = view.image_listOrder
        val nama = view.namaBarang_listOrder
        val harga = view.hargaBarang_listOrder
        val jumlah = view.jumlahBarang_listOrder
    }
}