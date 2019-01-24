package com.npe.galaxyorganic.ui.fragment.shop


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
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
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.datum.DatumShopItemModel
import com.npe.galaxyorganic.ui.model.datum.DatumShopMenuModel
import com.npe.galaxyorganic.ui.presenter.shop.ShopPresenter
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.alert_date_city.view.*
import kotlinx.android.synthetic.main.fragment_shop.view.*


class ShopFragment : Fragment(), ShopView.ShopItemView {

    private lateinit var recyclerMenu: RecyclerView
    private lateinit var recyclerItem: RecyclerView
    private lateinit var mAdapterMenu: AdapterShopFragment
    private lateinit var mAdapterItem: AdapterShopItemFragment
    private lateinit var buttonDate: Button
    private lateinit var datePicker: DatePickerDialog
    private lateinit var buttonArea : Button
    private lateinit var presenterShop : ShopPresenter
    //alert
    private var listNamaKota : MutableList<String> = mutableListOf<String>()
    private var listIdKota : MutableList<Int> = mutableListOf<Int>()

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
            presenterShop.setDate(tahun, bulan, hari)
        }, year, month, day)
        datePicker.show()
    }

    override fun displayAreaDialog(itemData: Array<String?>, checkedItem: Int) {
        val builder : AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Area")
            .setSingleChoiceItems(itemData, checkedItem){
                    dialog: DialogInterface?, which: Int ->
                presenterShop.checkedItem = which
                presenterShop.setArea(itemData[which].toString())
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

    override fun failedMenu(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    //alert
    override fun showAlertDialog(
        listNamaKota: MutableList<String>,
        listIdKota: MutableList<Int>
    ) {
        val dropNamaKota : Spinner
        val dialog : Dialog = Dialog(activity)
        dialog.setCancelable(true)

        val view : View = activity?.layoutInflater!!.inflate(R.layout.alert_date_city, null)
        dialog.setContentView(view)
        dropNamaKota = view.drop_city

        dropNamaKota.adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_dropdown_item, listNamaKota)
        dropNamaKota?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(context, dropNamaKota.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            }

        }
        dialog.show()
    }

    override fun alertNamaIdCity(listKota: ArrayList<DatumCitiesModel>) {
        for(i in listKota.indices){
            listNamaKota.add(i, listKota[i].name!!)
            listIdKota.add(i, listKota[i].id!!)
        }
        showAlertDialog(listNamaKota, listIdKota)
    }

    override fun onStart() {
        super.onStart()
        presenterShop.getlistCity()
    }
}
