package com.npe.galaxyorganic.ui.activity.shop

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.DatumShopItemModel
import kotlinx.android.synthetic.main.activity_detail_item_shop.*

class DetailItemShopActivity : AppCompatActivity() {

    private lateinit var jsonString: String
    private var gson = Gson()
    private lateinit var dataItem : DatumShopItemModel
    private var stockProduct : Int = 0
    private var maxStock : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item_shop)
        val getExtra = intent.extras
        if (getExtra != null) {
            jsonString = getExtra.getString("DataItem")
            dataItem = gson.fromJson(jsonString, DatumShopItemModel::class.java)
        }

        maxStock = dataItem.stock!!

        Glide.with(applicationContext)
            .load(dataItem.image)
            .into(imgToolbar)

        collapsingToolbar.title = dataItem.name
        tv_namaBarang_detailBarang.text = dataItem.name
        tv_hargaBarang_detailBarang.text = dataItem.sell_price
        tv_desc_detailBarang.text = dataItem.description


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

    private fun displayBuyingStock(stockProduct: Int) {
        tv_jumlahBarang_detailItem.text = stockProduct.toString()
    }
}
