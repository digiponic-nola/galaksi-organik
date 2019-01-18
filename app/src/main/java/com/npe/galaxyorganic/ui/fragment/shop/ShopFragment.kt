package com.npe.galaxyorganic.ui.fragment.shop


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.ShopItemModel
import com.npe.galaxyorganic.ui.model.ShopMenuModel
import com.npe.galaxyorganic.ui.presenter.shop.ShopItemPresenter
import com.npe.galaxyorganic.ui.presenter.shop.ShopMenuPresenter
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.fragment_shop.view.*


class ShopFragment : Fragment(), ShopView.ShopMenuView, ShopView.ShopItemView {



    private lateinit var recyclerMenu: RecyclerView
    private lateinit var recyclerItem: RecyclerView
    private lateinit var mAdapterMenu: AdapterShopFragment
    private lateinit var mAdapterItem: AdapterShopItemFragment
    private lateinit var buttonDate: Button
    private lateinit var datePicker: DatePickerDialog
    private lateinit var buttonArea : Button
    private lateinit var presenterItem : ShopItemPresenter
    private lateinit var presenterMenu : ShopMenuPresenter
    private lateinit var areaAlert : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_shop, container, false)

        recyclerMenu = v.recycler_menu_shop
        recyclerItem = v.recycler_all_list_shop
        buttonDate = v.btn_dateShipping_shop
        buttonArea = v.btn_areaShipping_shop

        presenterItem = ShopItemPresenter(this, requireContext())
        presenterMenu = ShopMenuPresenter(this, requireContext())

        //date shipping
        buttonDate.setOnClickListener {
            presenterItem.onDatePickerClicked()
        }

        //area shipping
        buttonArea.setOnClickListener {
            presenterItem.onAreaPickerClicked()
        }

        presenterMenu.getDataMenu()

        presenterItem.getAllItem()

        return v
    }


    override fun dataMenu(data: List<ShopMenuModel>) {
        recyclerMenu.layoutManager = GridLayoutManager(this!!.activity, 4)
        mAdapterMenu = AdapterShopFragment(requireContext(), data)
        recyclerMenu.adapter = mAdapterMenu
    }

    override fun dataItem(data: List<ShopItemModel>) {
        recyclerItem.layoutManager = GridLayoutManager(activity, 2)
        mAdapterItem = AdapterShopItemFragment(requireContext(), data)
        recyclerItem.adapter = mAdapterItem
    }

    override fun setDateText(date: String) {
        //buttonDate.text = """$dayOfMonth-${monthOfYear + 1}-$year"""
        buttonDate.text = date
    }

    override fun displayDatePickerDialog(year: Int, month: Int, day: Int) {
        datePicker = DatePickerDialog(context, DatePickerDialog.OnDateSetListener{
            view,tahun, bulan, hari ->
            presenterItem.setDate(tahun, bulan, hari)
        }, year, month, day)
        datePicker.show()
    }

    override fun displayAreaDialog(items: Array<String>, checkedItem: Int) {
        buttonArea.text = items[checkedItem]
        val builder : AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Area")
            .setSingleChoiceItems(items, checkedItem){
                    dialog: DialogInterface?, which: Int ->
                presenterItem.checkedItem = which
                presenterItem.setArea(items[which])
                dialog?.dismiss()
            }
            .setNeutralButton("Cancel"){
                    dialog, which ->
                dialog.cancel()
            }
        val mDialog = builder.create()
        mDialog.show()
    }

    override fun setAreaText(area: String) {
        //set area shipping
        buttonArea.text = area
    }
}
