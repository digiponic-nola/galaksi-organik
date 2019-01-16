package com.npe.galaxyorganic.ui.view

import com.npe.galaxyorganic.ui.model.ShopMenuModel

interface ShopView {
    interface ShopMenuView{
        fun dataMenu(data : List<ShopMenuModel>)
    }

    interface ShopMenuPresenterView{
        fun getDataMenu()
    }
}