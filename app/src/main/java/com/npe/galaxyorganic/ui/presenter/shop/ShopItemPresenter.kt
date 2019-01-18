package com.npe.galaxyorganic.ui.presenter.shop

import android.content.Context
import com.npe.galaxyorganic.ui.model.ShopItemModel
import com.npe.galaxyorganic.ui.view.ShopView
import java.text.SimpleDateFormat
import java.util.*


class ShopItemPresenter(val view: ShopView.ShopItemView, val context: Context) : ShopView.ListAllItemView {


    private var listItem = mutableListOf<ShopItemModel>()
    var checkedItem : Int = 0

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
        for (i in listNamaBarang.indices) {
            listItem.add(ShopItemModel(listNamaBarang[i], 0, listHargaBarang[i]))
        }

        view.dataItem(listItem)
    }

    override fun onDatePickerClicked() {
        var calendar : Calendar = Calendar.getInstance()
        var year : Int = calendar.get(Calendar.YEAR)
        var month : Int = calendar.get(Calendar.MONTH)
        var day : Int = calendar.get(Calendar.DAY_OF_MONTH)
        view.displayDatePickerDialog(year, month, day)
    }

    override fun setDate(year: Int, month: Int, day: Int) {
        var sdf = SimpleDateFormat("dd/MMM/yyyy")
        var calendar : Calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        var dateString : String = sdf.format(calendar.time)
        view.setDateText(dateString)
    }

    override fun onAreaPickerClicked() {
        val items = arrayOf("Malang", "Batu")
        view.displayAreaDialog(items, checkedItem)
    }

    override fun setArea(area: String) {
        view.setAreaText(area)
    }
}


