package com.npe.galaxyorganic.ui.presenter

import android.content.Context
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.ShopMenuModel
import com.npe.galaxyorganic.ui.view.ShopView

class ShopMenuPresenter(private val view: ShopView.ShopMenuView, private val context: Context) :
    ShopView.ShopMenuPresenterView {

    private val listMenu = mutableListOf<ShopMenuModel>()

    override fun getDataMenu() {
        val listData = context.resources.getStringArray(R.array.menu_shop)

        listMenu.clear()

        for (i in listData.indices) {
            listMenu.add(ShopMenuModel(R.drawable.ic_shopping_cart, listData[i]))
        }

        view.dataMenu(listMenu)
    }

}