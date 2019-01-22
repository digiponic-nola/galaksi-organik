package com.npe.galaxyorganic.ui.activity.shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.DatumShopItemModel
import com.npe.galaxyorganic.ui.presenter.shop.DetailItemPresenter
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.activity_detail_item_shop.*

class DetailItemShopActivity : AppCompatActivity(), ShopView.DetailItemShopView {

    private lateinit var jsonString: String
    private var stockProduct : Int = 0
    private var maxStock : Int = 0
    private lateinit var presenterDetailItem : DetailItemPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item_shop)
        val getExtra = intent.extras
        if (getExtra != null) {
            jsonString = getExtra.getString("DataItem")
        }

        presenterDetailItem = DetailItemPresenter(applicationContext, this)
        presenterDetailItem.getDetailItemFromProduct(jsonString)

        btn_addToCart_detailItem.setOnClickListener {
            showLayoutAddCart()
        }
    }

    private fun showLayoutAddCart() {
        btn_addToCart_detailItem.visibility = View.GONE
        layout_addToCart_detailItem.visibility = View.VISIBLE

        if(layout_addToCart_detailItem.visibility == View.VISIBLE){

            btn_addCart_detailItem.setOnClickListener {
                this.stockProduct = this.stockProduct + 1
                if(this.stockProduct > maxStock){
                    btn_addCart_detailItem.isClickable = false
                } else {
                    displayBuyingStock(this.stockProduct)
                }
            }

            btn_minusCart_detailItem.setOnClickListener {
                this.stockProduct = this.stockProduct - 1
                if(this.stockProduct <= 0){
                    this.stockProduct = 0
                    layout_addToCart_detailItem.visibility = View.GONE
                    btn_addToCart_detailItem.visibility = View.VISIBLE
                } else {
                   displayBuyingStock(this.stockProduct)
                }
            }
        }
    }

    override fun getDataDetailItem(data: DatumShopItemModel) {
        this.maxStock = data.stock!!
        Glide.with(applicationContext)
            .load(data.image)
            .into(imgToolbar)

        collapsingToolbar.title = data.name
        tv_namaBarang_detailBarang.text = data.name
                tv_hargaBarang_detailBarang.text = data.sell_price
        tv_desc_detailBarang.text = data.description
    }


    private fun displayBuyingStock(stockProduct: Int) {
        tv_jumlahBarang_detailItem.text = stockProduct.toString()
    }
}
