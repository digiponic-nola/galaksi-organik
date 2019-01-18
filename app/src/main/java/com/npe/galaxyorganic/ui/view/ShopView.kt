package com.npe.galaxyorganic.ui.view

import com.npe.galaxyorganic.ui.model.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.DatumShopMenuModel

interface ShopView {
    interface ShopMenuView {
        fun dataMenu(data: ArrayList<DatumShopMenuModel>)
        fun failedMenu(error : String)
    }

    interface ShopItemView {
        fun setDateText(date: String)
        fun setAreaText(area: String)
        fun displayDatePickerDialog(year: Int, month: Int, day: Int)
        fun displayAreaDialog(itemData: Array<String?>, checkedItem: Int)
        fun dataItem(data: List<DatumShopItemModel>)
        fun failedGetProduct(error: String)
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
    interface DetailMenuView{
        fun failed(error : String)
        fun dataItem(listProduct: ArrayList<DatumShopItemModel>)
    }

    interface ShopMenuDetailView{
        fun getProduct(categories_id : Int)
    }
}