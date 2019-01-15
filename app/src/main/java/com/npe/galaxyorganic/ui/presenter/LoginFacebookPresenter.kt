package com.npe.galaxyorganic.ui.presenter

import android.content.Intent
import com.facebook.CallbackManager
import com.npe.galaxyorganic.ui.view.LoginUserView

class LoginFacebookPresenter : LoginUserView.LoginFacebook {
    private lateinit var callbackManager: CallbackManager

    override fun initFB() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}