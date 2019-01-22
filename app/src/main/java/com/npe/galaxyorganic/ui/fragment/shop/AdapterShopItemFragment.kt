package com.npe.galaxyorganic.ui.fragment.shop

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.shop.DetailItemShopActivity
import com.npe.galaxyorganic.ui.model.datum.DatumShopItemModel
import kotlinx.android.synthetic.main.list_item_shop.view.*

class AdapterShopItemFragment(val context : Context, val items : List<DatumShopItemModel>) : RecyclerView.Adapter<AdapterShopItemFragment.ViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.list_item_shop, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        val list = items.get(p1)

        Glide.with(context)
            .load(list.image)
            .into(p0.imageBarang)

        p0.namaBarnag?.text = list.name
        p0.hargaBarang?.text = "Rp "+list.sell_price

        p0.btnBeli.setOnClickListener {
            var gson = Gson()
            var jsonString = gson.toJson(list)
            toDetailItem(context, jsonString)
        }

    }

    private fun toDetailItem(context: Context, list: String) {
        val intent = Intent(context, DetailItemShopActivity::class.java)
        Log.d("DataItem", list)
        intent.putExtra("DataItem", list)
        context.startActivity(intent)
    }

    class ViewHolder(view : View) :RecyclerView.ViewHolder(view){
        val imageBarang = view.imgv_listItem_shop
        val namaBarnag = view.tv_namaBarangListItem_shop
        val hargaBarang = view.tv_hargaBarangListItem_shop
        val btnBeli = view.btn_beliItemShop
    }
}

