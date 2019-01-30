package com.npe.galaxyorganic.ui.fragment.order

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.PaymentModel
import com.npe.galaxyorganic.ui.model.db.database
import kotlinx.android.synthetic.main.list_payment_oder.view.*

class AdapterOrder(val context: Context, val items: MutableList<PaymentModel>):RecyclerView.Adapter<AdapterOrder.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_payment_oder, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val list = items.get(p1)

        Glide.with(context)
            .load(R.drawable.ic_shopping_cart)
            .into(p0.image)
        p0.namaPenerima.text = list.namaPenerima
        p0.alamat.text = list.alamatPenerima
        p0.date.text = list.tanggalBeli
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val namaPenerima = view.tv_namaBarang_listPayment
        val alamat = view.tv_alamat_listPayment
        val date = view.tv_date_listPaymenet
        val image = view.imgv_listPayment
    }
}