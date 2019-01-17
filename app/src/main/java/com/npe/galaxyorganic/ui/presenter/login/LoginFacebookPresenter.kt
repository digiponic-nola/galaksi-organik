package com.npe.galaxyorganic.ui.presenter.login

import android.content.Intent
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.npe.galaxyorganic.ui.view.LoginView
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL

class LoginFacebookPresenter : LoginView.LoginFacebookView{
    private lateinit var mCallbackManager : CallbackManager

    override fun initFB() {
        mCallbackManager = CallbackManager.Factory.create()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun logIn(btnFacebook: LoginButton) {

    }


}