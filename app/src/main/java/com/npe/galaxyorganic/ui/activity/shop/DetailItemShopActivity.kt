package com.npe.galaxyorganic.ui.activity.shop

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.database
import com.npe.galaxyorganic.ui.presenter.shop.DetailItemPresenter
import com.npe.galaxyorganic.ui.utils.gone
import com.npe.galaxyorganic.ui.utils.visible
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.activity_detail_item_shop.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class DetailItemShopActivity : AppCompatActivity(), ShopView.DetailItemShopView {

    private lateinit var jsonString: String
    private var stockProduct: Int = 0
    private var maxStock: Int = 0
    private lateinit var presenterDetailItem: DetailItemPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item_shop)
        val getExtra = intent.extras
        if (getExtra != null) {
            jsonString = getExtra.getString("ID_ITEM")
        }

        presenterDetailItem = DetailItemPresenter(this)
        presenterDetailItem.getDetailItemFromProduct(jsonString)

        btn_addToCart_detailItem.setOnClickListener {
            showLayoutAddCart()
        }
    }

    private fun showLayoutAddCart() {
        btn_addToCart_detailItem.gone()
        layout_addToCart_detailItem.visible()
        btn_checkout.visible()
        if (layout_addToCart_detailItem.visibility == View.VISIBLE) {
            btn_addCart_detailItem.setOnClickListener {
                this.stockProduct = this.stockProduct + 1
                if (this.stockProduct > maxStock) {
                    btn_addCart_detailItem.isClickable = false
                } else {
                    displayBuyingStock(this.stockProduct)
                }
            }

            btn_minusCart_detailItem.setOnClickListener {
                this.stockProduct = this.stockProduct - 1
                if (this.stockProduct <= 0) {
                    this.stockProduct = 0
                    layout_addToCart_detailItem.gone()
                    btn_addToCart_detailItem.visible()
                    btn_checkout.gone()
                } else {
                    displayBuyingStock(this.stockProduct)
                }
            }

            btn_checkout.setOnClickListener {
                paymentActivity()
            }
        }
    }

    private fun paymentActivity() {
        presenterDetailItem.addBarangDBsql(applicationContext, tv_jumlahBarang_detailItem.text.toString())
        val intent = Intent(applicationContext, PaymentActivity::class.java)
        startActivity(intent)
    }

    override fun getDataDetailItem(data: Int) {
        val detailOrder: MutableList<OrderModel> = mutableListOf()
        application?.database?.use {
            val result = select(OrderModel.TABLE_ORDER)
                .whereArgs("(PRODUCT_ID = {product_id})", "product_id" to data)
            val data = result.parseList(classParser<OrderModel>())
            detailOrder.addAll(data)
        }
        this.maxStock = detailOrder.get(0).quantity
        Glide.with(applicationContext)
            .load(detailOrder.get(0).image_product)
            .into(imgToolbar)

        collapsingToolbar.title = detailOrder.get(0).product_name
        tv_namaBarang_detailBarang.text = detailOrder.get(0).product_name
        tv_hargaBarang_detailBarang.text = detailOrder.get(0).product_price.toString()
        //tv_desc_detailBarang.text = data.description
    }


    private fun displayBuyingStock(stockProduct: Int) {
        tv_jumlahBarang_detailItem.text = stockProduct.toString()
    }
}
