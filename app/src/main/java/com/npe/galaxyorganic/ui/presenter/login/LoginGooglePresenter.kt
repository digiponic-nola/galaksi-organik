package com.npe.galaxyorganic.ui.presenter.login

import android.content.ContentValues.TAG
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.npe.galaxyorganic.ui.view.LoginView

class LoginGooglePresenter : LoginView.LoginGoogleView {
    var auth = FirebaseAuth.getInstance()
    lateinit var viewLogin: LoginView.LoginUserView
    lateinit var viewAccount: LoginView.AccountUser

    companion object {
        lateinit var googleSignInClient: GoogleSignInClient
        lateinit var gso: GoogleSignInOptions
    }

    constructor(viewLogin: LoginView.LoginUserView) {
        this.viewLogin = viewLogin
    }

    constructor(viewAccount: LoginView.AccountUser) {
        this.viewAccount = viewAccount
    }


    override fun configureGoogle(
        default_web_client_id: String,
        requireActivity: FragmentActivity
    ) {
        Log.i("ConfigureGoogle", "Masuk")
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(default_web_client_id)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity, gso)
    }

    override fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        viewLogin.showLoading()
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                viewLogin.successLogin()
            } else {
                Log.w(TAG, "SignInWithCredentialFailure", task.exception)
                viewLogin.failedLogin(task.exception?.message.toString())
            }
            viewLogin.hideLoaidng()
        }
    }


    override fun loginGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        viewLogin.startActivityForResult(signInIntent)
    }

    override fun SignOut() {
        auth.signOut()
        googleSignInClient.signOut().addOnCompleteListener {

        }
    }

    override fun revokeAccess() {
        auth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener {
        }
    }
}
