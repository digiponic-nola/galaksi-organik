package com.npe.galaxyorganic.ui.activity.shop

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.npe.galaxyorganic.R
import kotlinx.android.synthetic.main.activity_detail_item_shop.*

class DetailItemShopActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_item_shop)

        val getExtra = intent.extras
        if(getExtra != null){
            Glide.with(applicationContext)
                .load(getExtra.getString("Image"))
                .into(imgv_detailBarang)

            tv_namaBarang_detailBarang.text = getExtra.getString("NamaBarang")
            tv_hargaBarang_detailBarang.text = getExtra.getString("HargaBarang")
            tv_desc_detailBarang.text = getExtra.getString("DetailBarang")
        }
    }
}
