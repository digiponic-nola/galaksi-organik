package com.npe.galaxyorganic.ui.presenter.shop

import android.content.Context
import com.npe.galaxyorganic.ui.model.ShopItemModel
import com.npe.galaxyorganic.ui.view.ShopView

class ShopItemPresenter(val view : ShopView.ShopItemView, val context : Context) : ShopView.ListAllItemView{

    private var listItem = mutableListOf<ShopItemModel>()

    override fun getAllItem() {
        //nama barang
        val listNamaBarang = arrayListOf(
            "Buah Naga",
            "Jeruk",
            "Jus Jeruk",
            "Ikan Tongkol",
            "Buah Naga",
            "Jeruk",
            "Jus Jeruk",
            "Ikan Tongkol"
        )

        //harga barang
        val listHargaBarang = arrayListOf(
            "Rp 17.000",
            "Rp 17.000",
            "Rp 17.000",
            "Rp 17.000",
            "Rp 17.000",
            "Rp 17.000",
            "Rp 17.000",
            "Rp 17.000"
        )

        listItem.clear()
        for (i in listNamaBarang.indices){
            listItem.add(ShopItemModel(listNamaBarang[i], 0, listHargaBarang[i]))
        }

        view.dataItem(listItem)
    }

}