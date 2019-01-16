package com.npe.galaxyorganic.ui.view

import com.npe.galaxyorganic.ui.model.ShopItemModel
import com.npe.galaxyorganic.ui.model.ShopMenuModel

interface ShopView {
    interface ShopMenuView{
        fun dataMenu(data : List<ShopMenuModel>)
    }

    interface ShopItemView{
        fun dataItem(data : List<ShopItemModel>)
    }

    interface ShopMenuPresenterView{
        fun getDataMenu()
    }

    interface ListAllItemView{
        fun getAllItem()
    }
}