package com.npe.galaxyorganic.ui.fragment.payment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.datum.DatumDistrikModel
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.presenter.payment.PaymentPresenter
import com.npe.galaxyorganic.ui.view.PaymentView
import kotlinx.android.synthetic.main.fragment_check_out.view.*
import org.jetbrains.anko.toast


class CheckOutFragment : Fragment(), PaymentView.PaymentUserView {

    private lateinit var presenterPayment: PaymentPresenter
    private lateinit var namaPenerima: TextView
    private lateinit var emailPenerima: TextView
    private lateinit var currentDate: TextView
    private lateinit var currentTime: String
    private lateinit var dropkota : Spinner
    private lateinit var dropDistrik : Spinner
    private var listNamaKota : MutableList<String> = mutableListOf()
    private var listNamaDistrik : MutableList<String> = mutableListOf()
    private var kota_id : Int = 0
    private lateinit var nama_kota : String
    private lateinit var nama_distrik : String
    private var customer_id: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_check_out, container, false)

        namaPenerima = v.tv_nama_checkout
        emailPenerima = v.tv_email_checkout
        currentDate = v.tv_tanggalPengiriman
        dropkota = v.drop_areaPengiriman
        dropDistrik = v.drop_distrikPengiriman
        //order
        presenterPayment = PaymentPresenter(this)
        presenterPayment.showDataOrderSQLDB(requireContext())
        //customer
        presenterPayment.showDataCustomerSQLDB(requireContext())
        //tanggal dan waktu
        presenterPayment.getCurrentDateTime()
        //area
        presenterPayment.getCityApi()
        //Log.d("NAMA_KOTA", dropkota.selectedItem.toString())
        return v
    }

    override fun dataOrder(dataOrder: MutableList<OrderModel>) {
        Log.d("DATA_ORDER", dataOrder.toString())
    }

    override fun dataCustomer(dataCustomer: MutableList<CustomerModel>) {
        Log.d("DATA_CUSTOMER_ORDER", dataCustomer.toString())
        //data user
        namaPenerima.text = dataCustomer.get(0).customer_name
        emailPenerima.text = dataCustomer.get(0).customer_email
        customer_id = dataCustomer.get(0).customer_id
    }

    override fun dataDateTime(dateTime: String) {
        val splited = dateTime.split(" ")
        //waktu dan tanggal
        currentDate.text = splited[0]
        currentTime = splited[1]
    }

    override fun dataKota(listDataCity: ArrayList<DatumCitiesModel>) {
        for (index in listDataCity.indices){
            listNamaKota.add(index, listDataCity.get(index).name!!)
        }

        Log.d("DATA_KOTA", listNamaKota.toString())
        dropkota.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKota)
        dropkota?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               //context?.toast("ID_KOTA"+listDataCity[position].states_id+" NAMA_KOTA "+dropkota.selectedItem.toString())
                kota_id = listDataCity[position].states_id!!
                presenterPayment.getDistrikApi(kota_id)
            }
        }

    }

    override fun dataDistrik(listDataDistrik: ArrayList<DatumDistrikModel>) {
        for (index in listDataDistrik.indices){
            listNamaDistrik.add(index, listDataDistrik.get(index).name!!)
        }
        dropDistrik.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listNamaDistrik)
        dropDistrik?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                nama_distrik = dropDistrik.selectedItem.toString()
            }
        }
    }
}
