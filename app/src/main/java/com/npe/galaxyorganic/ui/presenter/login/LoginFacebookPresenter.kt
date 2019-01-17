package com.npe.galaxyorganic.ui.presenter.login

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.view.LoginView

class LoginFacebookPresenter : LoginView.LoginFacebookView {
    private lateinit var mCallbackManager: CallbackManager
    private var loginView: LoginView.LoginUserView

    constructor(loginView: LoginView.LoginUserView) {
        this.loginView = loginView
    }


    override fun initFB() {
        mCallbackManager = CallbackManager.Factory.create()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onButtonClicked(activity: FragmentActivity?) {
        LoginManager.getInstance().registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookToken(result?.accessToken, activity)
            }

            override fun onCancel() {
                loginView.failedLogin("Gagal Error")
            }

            override fun onError(error: FacebookException?) {
                loginView.failedLogin(error?.message)
            }

        })
    }

    private fun handleFacebookToken(
        accessToken: AccessToken?,
        activity: FragmentActivity?
    ) {
        val auth = FirebaseAuth.getInstance()
        val credentialFacebook: AuthCredential = FacebookAuthProvider.getCredential(accessToken!!.token)
        auth.signInWithCredential(credentialFacebook).addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    val user = auth.currentUser
                    dataUserFacebook(user)
                    val intent = Intent(activity, MainActivity::class.java)
                    activity?.startActivity(intent)
                } else {

                }
            }
        })
    }

    private fun dataUserFacebook(user: FirebaseUser?) {
        Log.d("EmailFacebook", user?.email)
    }


}