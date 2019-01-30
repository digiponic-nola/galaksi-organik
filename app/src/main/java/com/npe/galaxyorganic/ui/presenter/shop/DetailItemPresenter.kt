package com.npe.galaxyorganic.ui.presenter.shop

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.google.gson.Gson
import com.npe.galaxyorganic.ui.model.datum.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.database
import com.npe.galaxyorganic.ui.view.ShopView
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailItemPresenter :
    ShopView.DetailItemPresenterView {
    private lateinit var gson: Gson
    private lateinit var data: DatumShopItemModel
    lateinit var view: ShopView.DetailItemShopView

    constructor(view: ShopView.DetailItemShopView) {
        this.view = view
    }


    override fun getDetailItemFromProduct(jsonString: String) {
        gson = Gson()
        data = gson.fromJson(jsonString, DatumShopItemModel::class.java)
        view.getDataDetailItem(data)
    }

    @SuppressLint("Recycle")
    override fun addBarangDBsql(context: Context, quantity: String) {
        //show barang
        Log.d("DATA_PRODUCT", this.data.toString())

        val product_id = this.data.id
        val product_name = this.data.name
        val product_price = this.data.sell_price?.toInt()
        val product_quantity = this.data.stock!!.toInt()
        val sub_total = product_price!! * product_quantity
        val buy_quantity = quantity.toInt()
        try {
            context?.database?.use {
                val query_replace = "REPLACE INTO TABLE_ORDER (" +
                        "product_id, product_name, product_price" +
                        ", quantity, sub_total, buy_quantity, product_image) VALUES " +
                        "(" + product_id + ", '" + product_name +
                        "', " + product_price + ", " + quantity +
                        ", " + sub_total + ", " + buy_quantity +
                        ", '" + OrderModel.PRODUCT_IMAGE + "');"

                val query_update = "UPDATE TABLE_ORDER SET buy_quantity = $buy_quantity"
                Log.d("QUERY REPLACE", query_replace)

                val cursor : Cursor = context?.database?.use {
                    rawQuery("SELECT * FROM TABLE_ORDER WHERE product_id = $product_id", null)
                }
                lateinit var s : String
                if (cursor.count < 1) {
                    execSQL(
                        query_replace
                    )
                    Log.d("DATANYA_ADA", "datanya GAK ADA")
                } else {
                    //Update

                    Log.d("DATANYA_ADA", "datanya ada")
                }





//                insert(
//                    OrderModel.TABLE_ORDER,
//                    OrderModel.PRODUCT_ID to product_id,
//                    OrderModel.PRODUCT_NAME to product_name,
//                    OrderModel.PRODUCT_PRICE to product_price,
//                    OrderModel.QUANTITY to product_quantity,
//                    OrderModel.SUB_TOTAL to sub_total
//                )
            }
            Log.d("INSERT_BARANG_SUCCESS", "MASUK")
        } catch (e: SQLiteConstraintException) {
            Log.d("INSERT_BARANG_FAILED", e.message)
        }
    }


}