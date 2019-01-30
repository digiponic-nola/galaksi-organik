package com.npe.galaxyorganic.ui.fragment.payment


import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.shop.AdapterDetailItemShop
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.datum.DatumDistrikModel
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.presenter.payment.PaymentPresenter
import com.npe.galaxyorganic.ui.view.PaymentView
import kotlinx.android.synthetic.main.fragment_check_out.view.*


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
    private lateinit var nama_distrik : String
    private lateinit var dateTime :String
    private var customer_id: Int = 0
    private lateinit var keteranganKirim : TextView
    private lateinit var alamatPenerima : EditText
    private lateinit var notelpPenerima : EditText
    private lateinit var subTotalHarga : TextView
    private lateinit var totalHarga : TextView
    private lateinit var recyclerOrder : RecyclerView
    private lateinit var adapterOrder : AdapterDetailItemShop
    private lateinit var btnPembayaran : Button

    companion object {
        var idDistrik : Int = 0
    }
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
        keteranganKirim = v.tv_keteranganKirim
        alamatPenerima = v.et_alamat_checkout
        notelpPenerima = v.et_notelp_checkout
        recyclerOrder = v.recycler_detailOrder
        subTotalHarga = v.tv_subtotal
        totalHarga = v.tv_totalHarga
        btnPembayaran = v.btn_pembayaran
        //order
        presenterPayment = PaymentPresenter(this)
        presenterPayment.showDataOrderSQLDB(requireContext())
        //customer
        presenterPayment.showDataCustomerSQLDB(requireContext())
        //tanggal dan waktu
        presenterPayment.getCurrentDateTime()
        //area
        presenterPayment.getCityApi()
        //waktu pengiriman
        presenterPayment.getWaktuPengiriman(currentTime)
        //Log.d("WAKTU_KIRIM",presenterPayment.getWaktuPengiriman(currentTime))
        btnPembayaran.setOnClickListener {
            pembayaran(
                customer_id,
                subTotalHarga.text.toString(),
                0,
                totalHarga.text.toString(),
                dateTime,
                1,
                idDistrik,
                namaPenerima.text.toString(),
                emailPenerima.text.toString()
            )
        }
        return v

    }

    private fun pembayaran(customer_id: Int, total: String?, discount: Int, grand_total: String?, dateTime: String,
                           id_status: Int, id_distrik: Int, namaPenerima: String?, emailPenerima: String?) {

        Log.d("DATA_PEMBAYARAN", customer_id.toString() + " , " +
        total + " , "+ discount.toString()+" , "+ grand_total+" , "+ dateTime + " , "+ id_status.toString() +" , "+id_distrik.toString() +" , "
        + namaPenerima + " , "+emailPenerima)

    }

    override fun dataOrder(dataOrder: MutableList<OrderModel>) {
        Log.d("DATA_ORDER", dataOrder.toString())
        //sub dan total
        subTotalHarga.text = dataOrder.get(0).sub_total.toString()
        totalHarga.text = dataOrder.get(0).sub_total.toString()
        //recycler
        recyclerOrder.layoutManager =  LinearLayoutManager(context, LinearLayout.VERTICAL, false)
        adapterOrder = AdapterDetailItemShop(requireContext(), dataOrder)
        recyclerOrder.adapter = adapterOrder
    }

    override fun dataCustomer(dataCustomer: MutableList<CustomerModel>) {
        Log.d("DATA_CUSTOMER_ORDER", dataCustomer.toString())
        //data user
        namaPenerima.text = dataCustomer.get(0).customer_name
        emailPenerima.text = dataCustomer.get(0).customer_email
        customer_id = dataCustomer.get(0).customer_id
    }

    override fun dataDateTime(dateTime: String) {
        this.dateTime = dateTime
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
                CheckOutFragment.idDistrik = listDataDistrik.get(position).id!!
            }
        }
    }

    override fun dataWaktuKirim(waktu: String) {
        Log.d("JAM_FRAGMENT", waktu)
        keteranganKirim.text = "Waktu Pembelian $currentTime , maka pengiriman akan dilakukan pada hari $waktu"
    }
}
