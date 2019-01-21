package com.npe.galaxyorganic.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.fragment.login.LoginFragment
import kotlinx.android.synthetic.main.fragment_login_first.view.*

class LoginFirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_login_first, container, false)

        v.btn_toLogin.setOnClickListener {
            toLogin(savedInstanceState)
        }

        return v
    }

    private fun toLogin(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_frame, LoginFragment(), LoginFragment::class.java.simpleName)
                ?.commit()
        }
    }


}
