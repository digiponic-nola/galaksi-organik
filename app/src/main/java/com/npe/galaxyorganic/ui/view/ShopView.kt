package com.npe.galaxyorganic.ui.view

import com.npe.galaxyorganic.ui.model.datum.DatumShopDetailMenuModel
import com.npe.galaxyorganic.ui.model.datum.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.datum.DatumShopMenuModel

interface ShopView {
    interface ShopItemView {
        fun setDateText(date: String, timeString: String)
        fun setAreaText(area: String)
        fun displayDatePickerDialog(year: Int, month: Int, day: Int)
        fun displayAreaDialog(itemData: Array<String?>, checkedItem: Int)
        fun dataItem(data: List<DatumShopItemModel>)
        fun failedGetProduct(error: String)
        fun dataMenu(data: ArrayList<DatumShopMenuModel>)
        fun failedMenu(error : String)
    }

    interface ListAllItemView {
        fun onDatePickerClicked()
        fun onAreaPickerClicked()
        fun setArea(area: String)
        fun setDate(year: Int, month: Int, day: Int)
        fun getAllItem()
        fun getDataMenu()
    }
    interface DetailMenuView{
        fun failed(error : String)
        fun dataItem(listProduct: ArrayList<DatumShopDetailMenuModel>)
    }

    interface ShopMenuDetailView{
        fun getProduct(categories_id : Int)
    }

    interface DetailItemShopView{
        fun getDataDetailItem(data: DatumShopItemModel)
    }

    interface DetailItemPresenterView{
        fun getDetailItemFromProduct(jsonString: String)
    }
}