package com.npe.galaxyorganic.ui.fragment.login


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.facebook.*
import com.facebook.login.widget.LoginButton

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.presenter.LoginPresenter
import com.npe.galaxyorganic.ui.view.LoginView
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONException
import java.util.*
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL


class LoginFragment : Fragment() , LoginView.LoginUserView {

    private lateinit var btnFacebook : LoginButton
    private lateinit var btnGoogle : Button
    private lateinit var presenterFacebook : LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        btnFacebook = v.btn_login_facebook
        btnGoogle = v.btn_login_google

        presenterFacebook = LoginPresenter()

        presenterFacebook.initFB()

        btnFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"))
        btnFacebook.setFragment(this)
        btnFacebook.setOnClickListener {
            presenterFacebook.logIn(btnFacebook)
        }

        btnGoogle.setOnClickListener {
            loginGoogle()
        }

        return v
    }



    private fun loginGoogle() {
        Toast.makeText(context, "Google", Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenterFacebook.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoaidng() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResume() {
        super.onResume()
        if(AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired){
            Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
