package com.npe.galaxyorganic.ui.view

import com.npe.galaxyorganic.ui.model.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.ShopMenuModel

interface ShopView {
    interface ShopMenuView {
        fun dataMenu(data: List<ShopMenuModel>)
    }

    interface ShopItemView {
        fun setDateText(date: String)
        fun setAreaText(area: String)
        fun displayDatePickerDialog(year: Int, month: Int, day: Int)
        fun displayAreaDialog(items: Array<String>, checkedItem: Int)
        fun dataItem(data: List<DatumShopItemModel>)
        fun failedGetProduct()
    }

    interface ShopMenuPresenterView {
        fun getDataMenu()
    }

    interface ListAllItemView {
        fun onDatePickerClicked()
        fun onAreaPickerClicked()
        fun setArea(area: String)
        fun setDate(year: Int, month: Int, day: Int)
        fun getAllItem()
    }
}