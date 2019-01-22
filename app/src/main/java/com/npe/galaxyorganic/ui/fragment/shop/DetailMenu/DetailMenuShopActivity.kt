package com.npe.galaxyorganic.ui.fragment.shop.DetailMenu

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.datum.DatumShopDetailMenuModel
import com.npe.galaxyorganic.ui.presenter.shop.DetailMenuPresenter
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.activity_detail_menu_shop.*

class DetailMenuShopActivity : AppCompatActivity(), ShopView.DetailMenuView {


    private lateinit var presenterDetailMenu : DetailMenuPresenter
    private lateinit var adapterDetailMenu : AdapterDetailMenuShop
    private var idCategory : Int = 0
    private lateinit var Category : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_menu_shop)


        val getExtras : Bundle = intent.extras
        if(getExtras != null){
            tv_titleDetailMenu_shop.text = getExtras.getString("Category")
            Category = getExtras.getString("Category")
            idCategory = getExtras.getInt("idCategory")
        }


        presenterDetailMenu = DetailMenuPresenter(applicationContext, this)
        presenterDetailMenu.getProduct(idCategory)
    }

    override fun failed(error: String) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_SHORT).show()
    }
    override fun dataItem(listProduct: ArrayList<DatumShopDetailMenuModel>) {
        recycler_detailMenuShop.layoutManager = GridLayoutManager(this@DetailMenuShopActivity, 2)
        adapterDetailMenu = AdapterDetailMenuShop(applicationContext, listProduct)
        recycler_detailMenuShop.adapter = adapterDetailMenu

    }
}
