package com.npe.galaxyorganic.ui.activity.shop

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.datum.DatumItemOrder
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.bottom_sheet.view.*

class BottomSheetActivity : AppCompatActivity() {

    private lateinit var recyclerItem: RecyclerView
    private lateinit var mAdapterItem: BottomSheetRVAdapter
    private lateinit var data: ArrayList<DatumItemOrder>

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.bottom_sheet)

        recyclerItem = rv_item_bottomSheet

        recyclerItem.layoutManager = LinearLayoutManager(this@BottomSheetActivity, LinearLayout.VERTICAL, false)

        data.add(DatumItemOrder(1, "Buncis","11000", "1"))
        mAdapterItem = BottomSheetRVAdapter(this@BottomSheetActivity, data)
        recyclerItem.adapter = mAdapterItem

    }

}