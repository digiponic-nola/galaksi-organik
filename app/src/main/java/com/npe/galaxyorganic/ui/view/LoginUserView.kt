package com.npe.galaxyorganic.ui.view

import android.content.Intent


interface LoginUserView {
    interface LoginFacebook {
        fun initFB()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
        fun logIn()
    }

    interface LoginView {
        fun showLoading()
        fun hideLoading()
    }
}
