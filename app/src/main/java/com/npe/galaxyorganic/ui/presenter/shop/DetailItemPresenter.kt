package com.npe.galaxyorganic.ui.presenter.shop

import android.content.Context
import com.google.gson.Gson
import com.npe.galaxyorganic.ui.model.datum.DatumShopItemModel
import com.npe.galaxyorganic.ui.view.ShopView

class DetailItemPresenter(val context: Context, val view: ShopView.DetailItemShopView) :
    ShopView.DetailItemPresenterView {
    private lateinit var gson: Gson
    private lateinit var data: DatumShopItemModel

    override fun getDetailItemFromProduct(jsonString: String) {
        gson = Gson()
        data = gson.fromJson(jsonString, DatumShopItemModel::class.java)
        view.getDataDetailItem(data)
    }





}