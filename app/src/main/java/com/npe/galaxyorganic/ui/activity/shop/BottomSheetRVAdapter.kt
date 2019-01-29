package com.npe.galaxyorganic.ui.activity.shop

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.fragment.shop.AdapterShopFragment
import com.npe.galaxyorganic.ui.model.datum.DatumItemOrder
import com.npe.galaxyorganic.ui.model.datum.DatumShopMenuModel
import kotlinx.android.synthetic.main.bottom_sheet_item.view.*
import kotlinx.android.synthetic.main.list_menu_shop.view.*

class BottomSheetRVAdapter (val context : Context, val items : List<DatumItemOrder>) : RecyclerView.Adapter<BottomSheetRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return BottomSheetRVAdapter.ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout. bottom_sheet_item, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: BottomSheetRVAdapter.ViewHolder, p1: Int) {
        val list = items.get(p1)

        p0.namaBarang.text = list.name
        p0.hargaBarang.text = list.price
        p0.jumlahBarang.text = list.count

    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val namaBarang = view.namaBarang_bottomSheet
        val hargaBarang = view.hargaBarang_bottomSheet
        val jumlahBarang = view.jumlahBarang_bottomSheet
    }


}