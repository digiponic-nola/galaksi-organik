package com.npe.galaxyorganic.ui.fragment.shop


import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.PorterDuff
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
import java.text.SimpleDateFormat
import java.util.*

class ShopFragment : Fragment(), ShopView.ShopMenuView, ShopView.ShopItemView {

    private lateinit var recyclerMenu: RecyclerView
    private lateinit var recyclerItem: RecyclerView
    private lateinit var mAdapterMenu: AdapterShopFragment
    private lateinit var mAdapterItem: AdapterShopItemFragment
    private lateinit var buttonDate: Button
    private lateinit var calender: Calendar
    private lateinit var datePicker: DatePickerDialog
    private lateinit var spinnerArea: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_shop, container, false)

        recyclerMenu = v.recycler_menu_shop
        recyclerItem = v.recycler_all_list_shop
        buttonDate = v.btn_dateShipping_shop
        spinnerArea = v.drop_shippingArea_shop

        val presenterItem = ShopItemPresenter(this, requireContext())
        val presenterMenu = ShopMenuPresenter(this, requireContext())

        //date shipping
        buttonDate.setOnClickListener {
            calender = Calendar.getInstance()
            val day: Int = calender.get(Calendar.DAY_OF_MONTH)
            val month: Int = calender.get(Calendar.MONTH)
            val year: Int = calender.get(Calendar.YEAR)

            datePicker = DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var sdf = SimpleDateFormat("dd/MMM/yyyy")
                    calender.set(year, monthOfYear, dayOfMonth)
                    var dateString : String = sdf.format(calender.time)
                    buttonDate.text = dateString
                    //buttonDate.text = """$dayOfMonth-${monthOfYear + 1}-$year"""
                },
                year,
                month,
                day
            )
            datePicker.show()
        }
        //area shipping
        var dataArea = ArrayAdapter.createFromResource(context, R.array.area_shipping, android.R.layout.simple_spinner_dropdown_item)
        dataArea.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerArea.adapter = dataArea
        spinnerArea.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)


        spinnerArea.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(context, spinnerArea.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            }

        }

        presenterMenu.getDataMenu()

        presenterItem.getAllItem()

        return v
    }


    override fun dataMenu(data: List<ShopMenuModel>) {
        recyclerMenu.layoutManager = GridLayoutManager(activity, 4)
        mAdapterMenu = AdapterShopFragment(requireContext(), data)
        recyclerMenu.adapter = mAdapterMenu
    }

    override fun dataItem(data: List<ShopItemModel>) {
        recyclerItem.layoutManager = GridLayoutManager(activity, 2)
        mAdapterItem = AdapterShopItemFragment(requireContext(), data)
        recyclerItem.adapter = mAdapterItem
    }

}
