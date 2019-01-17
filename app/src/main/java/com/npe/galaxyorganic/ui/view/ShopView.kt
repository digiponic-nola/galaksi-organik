package com.npe.galaxyorganic.ui.view

import com.npe.galaxyorganic.ui.model.ShopItemModel
import com.npe.galaxyorganic.ui.model.ShopMenuModel

interface ShopView {
    interface ShopMenuView{
        fun dataMenu(data : List<ShopMenuModel>)
    }

    interface ShopItemView{
        fun setDateText(date : String)
        fun displayDatePickerDialog(year : Int, month : Int, day : Int)
        fun dataItem(data : List<ShopItemModel>)
    }

    interface ShopMenuPresenterView{
        fun getDataMenu()
    }

    interface ListAllItemView{
        fun onDatePickerClicked()
        fun setDate(year : Int, month : Int, day : Int)
        fun getAllItem()
    }
}