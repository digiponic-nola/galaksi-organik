package com.npe.galaxyorganic.ui.view

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser

interface LoginView {
    interface LoginUserView {
        fun showLoading()
        fun hideLoaidng()
        fun successLogin()
        fun failedLogin(message: String?)
        fun startActivityForResult(signInIntent: Intent)
    }

    interface AccountUser {
        fun dataUser()
    }

    interface LoginFacebookView {
        fun initFB()
        fun onButtonClicked(activity: FragmentActivity?)
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)

    }

    interface LoginGoogleView {
        fun configureGoogle(
            default_web_client_id: String,
            requireActivity: FragmentActivity
        )
        fun firebaseAuthWithGoogle(account: GoogleSignInAccount?)
        fun loginGoogle()
        fun SignOut()
        fun revokeAccess()
    }
}