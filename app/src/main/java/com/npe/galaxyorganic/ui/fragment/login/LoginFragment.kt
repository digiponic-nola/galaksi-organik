package com.npe.galaxyorganic.ui.fragment.login


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

import com.npe.galaxyorganic.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var btnFacebook : Button
    private lateinit var btnGoogle : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        btnFacebook = v.btn_login_facebook
        btnGoogle = v.btn_login_google

        btnFacebook.setOnClickListener {
            loginFacebook()
        }

        btnGoogle.setOnClickListener {
            loginGoogle()
        }

        return v
    }

    private fun loginGoogle() {
        Toast.makeText(context, "Google", Toast.LENGTH_SHORT).show()
    }

    private fun loginFacebook() {
        Toast.makeText(context, "Facebook", Toast.LENGTH_SHORT).show()
    }


}
