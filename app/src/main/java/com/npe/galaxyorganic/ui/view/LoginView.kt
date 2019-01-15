package com.npe.galaxyorganic.ui.view

import android.content.Intent
import com.facebook.login.widget.LoginButton

interface LoginView{
    interface LoginUserView{
        fun showLoading()
        fun hideLoaidng()
    }

    interface LoginFacebookView{
        fun initFB()
        fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent)
        fun logIn(btnFacebook: LoginButton)
    }
}