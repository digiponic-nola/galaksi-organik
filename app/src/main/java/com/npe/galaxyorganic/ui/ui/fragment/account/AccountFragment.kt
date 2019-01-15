package com.npe.galaxyorganic.ui.ui.fragment.account


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.login.widget.LoginButton

import com.npe.galaxyorganic.R
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment() {

    private lateinit var btnLogoutFacebook : LoginButton
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_account, container, false)

        btnLogoutFacebook = v.btn_logout_facebook



        return v
    }


}
