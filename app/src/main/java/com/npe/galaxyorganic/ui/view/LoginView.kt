package com.npe.galaxyorganic.ui.view

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.view.View
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface LoginView{
    interface LoginUserView{
        fun showLoading()
        fun hideLoaidng()
        fun dataFromGoogle(user: FirebaseUser?)
        fun loginGoogle()
        fun successLogin()
        fun failedLogin(message: String?)
    }

    interface LoginFacebookView{
        fun initFB()
        fun onButtonClicked(view : View)
        fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent)
    }

    interface LoginGoogleView{
        fun configureGoogle(default_web_client_id : String)
        fun firebaseAuthWithGoogle(
            account: GoogleSignInAccount?,
            requireActivity: FragmentActivity
        )
        fun logOutGoogle()
        fun revokeAccess()
    }
}