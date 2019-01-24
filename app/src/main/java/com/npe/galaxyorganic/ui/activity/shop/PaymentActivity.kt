package com.npe.galaxyorganic.ui.activity.shop

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.fragment.login.LoginFragment
import com.npe.galaxyorganic.ui.fragment.payment.CheckOutFragment

class PaymentActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if(user != null){
            loadCheckOutFragment(savedInstanceState)
        } else {
            loadLoginFragment(savedInstanceState)
        }
    }

    private fun loadLoginFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_checkout, LoginFragment(), LoginFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadCheckOutFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame_checkout, CheckOutFragment(), CheckOutFragment::class.java.simpleName)
                .commit()
        }
    }

}
