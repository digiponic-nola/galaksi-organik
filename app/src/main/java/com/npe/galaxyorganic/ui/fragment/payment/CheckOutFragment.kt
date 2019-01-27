package com.npe.galaxyorganic.ui.fragment.payment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.db.CustomerModel
import com.npe.galaxyorganic.ui.model.db.OrderModel
import com.npe.galaxyorganic.ui.presenter.login.LoginPresenter
import com.npe.galaxyorganic.ui.presenter.payment.PaymentPresenter
import com.npe.galaxyorganic.ui.view.PaymentView


class CheckOutFragment : Fragment(), PaymentView.PaymentUserView {

    private lateinit var presenterPayment : PaymentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_check_out, container, false)

        //order
        presenterPayment = PaymentPresenter(this)
        presenterPayment.showDataOrderSQLDB(requireContext())
        //customer
        presenterPayment.showDataCustomerSQLDB(requireContext())
        return v
    }

    override fun dataOrder(dataOrder: MutableList<OrderModel>) {
        Log.d("DATA_ORDER", dataOrder.toString())
    }

    override fun dataCustomer(dataCustomer: MutableList<CustomerModel>) {
        Log.d("DATA_CUSTOMER_ORDER", dataCustomer.toString())
    }
}
