package com.npe.galaxyorganic.ui.fragment.shop


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.datum.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.datum.DatumShopMenuModel
import com.npe.galaxyorganic.ui.presenter.shop.ShopPresenter
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.fragment_shop.view.*
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.database
import org.jetbrains.anko.db.*


class ShopFragment : Fragment(), ShopView.ShopItemView {

    private lateinit var recyclerMenu: RecyclerView
    private lateinit var recyclerItem: RecyclerView
    private lateinit var mAdapterMenu: AdapterShopFragment
    private lateinit var mAdapterItem: AdapterShopItemFragment
    private lateinit var buttonDate: Button
    private lateinit var datePicker: DatePickerDialog
    private lateinit var buttonArea: Button
    private lateinit var presenterShop: ShopPresenter

    private var orderData: MutableList<OrderModel> = mutableListOf()

    override fun onResume() {
        super.onResume()
        context?.database!!.use {
            delete(OrderModel.TABLE_ORDER)
            Log.d("INSERT DROP", "DROP SUCCESS")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_shop, container, false)


        recyclerMenu = v.recycler_menu_shop
        recyclerItem = v.recycler_all_list_shop
        buttonDate = v.btn_dateShipping_shop
        buttonArea = v.btn_areaShipping_shop

        presenterShop = ShopPresenter(this)

        //date shipping
        buttonDate.setOnClickListener {
            presenterShop.onDatePickerClicked()
        }

        //area shipping
        buttonArea.setOnClickListener {
            presenterShop.onAreaPickerClicked()
        }

        presenterShop.getDataMenu()

        presenterShop.getAllItem()

        return v
    }

    override fun failedGetProduct(s: String) {
        Toast.makeText(context, "Gagal Menampilkan Product", Toast.LENGTH_SHORT).show()
    }

    override fun dataMenu(data: ArrayList<DatumShopMenuModel>) {
        recyclerMenu.layoutManager = GridLayoutManager(this!!.activity, 4)
        mAdapterMenu = AdapterShopFragment(requireContext(), data)
        recyclerMenu.adapter = mAdapterMenu
    }

    override fun dataItem(data: List<DatumShopItemModel>) {
        val buyDefault = 0
        for (i in data.indices) {
            try {
                context!!.database.use {
                    insert(
                        OrderModel.TABLE_ORDER,
                        OrderModel.PRODUCT_ID to data.get(i).id,
                        OrderModel.PRODUCT_NAME to data.get(i).name,
                        OrderModel.PRODUCT_PRICE to data.get(i).sell_price,
                        OrderModel.QUANTITY to data.get(i).stock,
                        OrderModel.SUB_TOTAL to buyDefault,
                        OrderModel.BUY_QUANTITY to buyDefault,
                        OrderModel.IMAGE_PRODUCT to data.get(i).image
                    )
                }
                Log.d("INSERT SUCCESS","INSERT SUCCESS")
            } catch (e: SQLiteConstraintException) {
                Log.d("INSERT ERROR", "INSERT ERROR")
            }

        }
        try {
            context!!.database.use {
                val result = select(OrderModel.TABLE_ORDER)
                val data = result.parseList(classParser<OrderModel>())
                orderData.addAll(data)

            }
        } catch (e : SQLiteConstraintException) {

        }
        recyclerItem.layoutManager = GridLayoutManager(activity, 2)
        mAdapterItem = AdapterShopItemFragment(requireContext(), orderData)
        recyclerItem.adapter = mAdapterItem
    }

    override fun setDateText(date: String, timeString: String) {
        //buttonDate.text = """$dayOfMonth-${monthOfYear + 1}-$year"""
        Log.d("TIME", timeString)
        buttonDate.text = date
    }

    override fun displayDatePickerDialog(year: Int, month: Int, day: Int) {
        datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, tahun, bulan, hari ->
            presenterShop.setDate(tahun, bulan, hari)
        }, year, month, day)
        datePicker.show()
    }

    override fun displayAreaDialog(itemData: Array<String?>, checkedItem: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Area")
            .setSingleChoiceItems(itemData, checkedItem) { dialog: DialogInterface?, which: Int ->
                presenterShop.checkedItem = which
                presenterShop.setArea(itemData[which].toString())
                dialog?.dismiss()
            }
            .setNeutralButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
        val mDialog = builder.create()
        mDialog.show()
    }

    override fun setAreaText(area: String) {
        //set area shipping
        buttonArea.text = area
    }

    override fun failedMenu(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

}
