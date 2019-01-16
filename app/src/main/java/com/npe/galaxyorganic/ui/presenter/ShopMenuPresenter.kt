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
        val iconMenu = arrayListOf(
            R.drawable.icon_makanan,
            R.drawable.icon_sayur,
            R.drawable.icon_beras,
            R.drawable.icon_jus,
            R.drawable.icon_fish)
        listMenu.clear()

        for (i in listData.indices) {
            listMenu.add(ShopMenuModel(iconMenu[i], listData[i]))
        }
        view.dataMenu(listMenu)
    }

}