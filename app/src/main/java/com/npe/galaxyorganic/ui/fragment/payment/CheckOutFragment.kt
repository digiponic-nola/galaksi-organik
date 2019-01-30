package com.npe.galaxyorganic.ui.fragment.payment


import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.activity.shop.AdapterDetailItemShop
import com.npe.galaxyorganic.ui.model.datum.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.datum.DatumDistrikModel
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.model.db.PaymentModel
import com.npe.galaxyorganic.ui.model.db.database
import com.npe.galaxyorganic.ui.presenter.payment.PaymentPresenter
import com.npe.galaxyorganic.ui.view.PaymentView
import kotlinx.android.synthetic.main.fragment_check_out.view.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast


class CheckOutFragment : Fragment(), PaymentView.PaymentUserView {

    private lateinit var presenterPayment: PaymentPresenter
    private lateinit var namaPenerima: TextView
    private lateinit var emailPenerima: TextView
    private lateinit var currentDate: TextView
    private lateinit var currentTime: String
    private lateinit var dropkota: Spinner
    private lateinit var dropDistrik: Spinner
    private var listNamaKota: MutableList<String> = mutableListOf()
    private var listNamaDistrik: MutableList<String> = mutableListOf()
    private var kota_id: Int = 0
    private lateinit var nama_distrik: String
    private lateinit var dateTime: String
    private var customer_id: Int = 0
    private lateinit var keteranganKirim: TextView
    private lateinit var alamatPenerima: EditText
    private lateinit var notelpPenerima: EditText
    private lateinit var subTotalHarga: TextView
    private lateinit var totalHarga: TextView
    private lateinit var recyclerOrder: RecyclerView
    private lateinit var adapterOrder: AdapterDetailItemShop
    private lateinit var btnPembayaran: Button
    private lateinit var etAlamat: EditText
    private lateinit var etNotelp: EditText

    companion object {
        var idDistrik: Int = 0
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
        etAlamat = v.et_alamat_checkout
        etNotelp = v.et_notelp_checkout

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
            if(!etAlamat.text.isEmpty()){
                if(!etNotelp.text.isEmpty()){
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
                } else {
                    context?.toast("Notelp Masih Kosong")
                }
            } else {
                context?.toast("Alamat Masih Kosong")
            }
        }
        return v

    }

    private fun pembayaran(
        customer_id: Int, total: String?, discount: Int, grand_total: String?, dateTime: String,
        id_status: Int, id_distrik: Int, namaPenerima: String?, emailPenerima: String?
    ) {
        var alamatPenerima: String = etAlamat.text.toString()
        var notelpPenerima: String = etNotelp.text.toString()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("PEMBAYARAN")
        builder.setMessage("Data yang tertera sudah benar?")
        builder.setPositiveButton("BENAR") { dialog, which ->
            // Do something when user press the positive button
            addDataPaymentDbSql(
                namaPenerima, emailPenerima,
                dateTime, alamatPenerima, notelpPenerima,
                total.toString(), discount.toString(), grand_total.toString()
            )

            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            //Toast.makeText(context,"Ok, we change the app background.",Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("CANCEL") { _, _ ->
            Toast.makeText(context, "You cancelled the dialog.", Toast.LENGTH_SHORT).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun addDataPaymentDbSql(
        namaPenerima: String?,
        emailPenerima: String?,
        dateTime: String,
        alamatPenerima: String,
        notelpPenerima: String,
        subTotalHarga: String,
        discount: String,
        totalHarga1: String
    ) {
        Log.d(
            "DATA_KE_PEMBAYARAN",
            namaPenerima + " , " + emailPenerima + " , " + dateTime + " , " + alamatPenerima + " , " +
                    notelpPenerima + " , " + subTotalHarga + " , " + discount + " , " + totalHarga1
        )
        try {
            context?.database?.use {
                insert(
                    PaymentModel.TABLE_PAYMENT,
                    PaymentModel.NAMA_PENERIMA to namaPenerima,
                    PaymentModel.EMAIL_PENERIMA to emailPenerima,
                    PaymentModel.TANGGAL_BELI to dateTime,
                    PaymentModel.ALAMAT_PENERIMA to alamatPenerima,
                    PaymentModel.NOTELP_PENERIMA to notelpPenerima,
                    PaymentModel.SUBTOTAL to subTotalHarga,
                    PaymentModel.DISKON to discount,
                    PaymentModel.TOTAL_HARGA to totalHarga1
                )
            }
            Log.d("BERHASIL kirim payment", "Berhasil")
        } catch (e: SQLiteConstraintException) {
            Log.d("Gagal Kirim ke Payment", "GaGAL")
        }
    }

    override fun dataOrder(dataOrder: MutableList<OrderModel>) {
        Log.d("DATA_ORDER", dataOrder.toString())
        //sub dan total
        subTotalHarga.text = dataOrder.get(0).sub_total.toString()
        totalHarga.text = dataOrder.get(0).sub_total.toString()
        //recycler
        recyclerOrder.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
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
        for (index in listDataCity.indices) {
            listNamaKota.add(index, listDataCity.get(index).name!!)
        }

        Log.d("DATA_KOTA", listNamaKota.toString())
        dropkota.adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listNamaKota)
        dropkota?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        for (index in listDataDistrik.indices) {
            listNamaDistrik.add(index, listDataDistrik.get(index).name!!)
        }
        dropDistrik.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, listNamaDistrik)
        dropDistrik?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
