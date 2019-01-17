package com.npe.galaxyorganic.ui.view

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.npe.galaxyorganic.ui.model.ShopItemModel
import com.npe.galaxyorganic.ui.model.ShopMenuModel

interface ShopView {
    interface ShopMenuView{
        fun dataMenu(data : List<ShopMenuModel>)
    }

    interface ShopItemView{
        fun setDateText(date : String)
        fun setAreaText(area : String)
        fun displayDatePickerDialog(year : Int, month : Int, day : Int)
        fun displayAreaDialog(dataArea: ArrayAdapter<CharSequence>)
        fun dataItem(data : List<ShopItemModel>)
    }

    interface ShopMenuPresenterView{
        fun getDataMenu()
    }

    interface ListAllItemView{
        fun onDatePickerClicked()
        fun onAreaPickerClicked()
        fun setArea(
            parent: AdapterView<*>?,
            viewShop: View?,
            position: Int,
            id: Long
        )
        fun setDate(year : Int, month : Int, day : Int)
        fun getAllItem()
    }
}