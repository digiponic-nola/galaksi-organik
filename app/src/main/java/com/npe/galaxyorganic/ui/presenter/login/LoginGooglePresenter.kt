package com.npe.galaxyorganic.ui.presenter.login

import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.fragment.login.LoginFragment
import com.npe.galaxyorganic.ui.view.LoginView

class LoginGooglePresenter : LoginView.LoginGoogleView {
    private lateinit var mGso: GoogleSignInOptions
    private var loginUserView: LoginView.LoginUserView

    constructor(loginUserView: LoginView.LoginUserView) {
        this.loginUserView = loginUserView
    }


    override fun configureGoogle(default_web_client_id: String) {
        mGso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(default_web_client_id)
            .requestEmail()
            .build()

        LoginFragment.gso = mGso
    }

    override fun firebaseAuthWithGoogle(
        account: GoogleSignInAccount?,
        requireActivity: FragmentActivity
    ) {
        var auth = FirebaseAuth.getInstance()
        if (account != null) {
            Log.d("ID_USER_GOOGLE", account.id)
        }

        loginUserView.showLoading()

        val credetial = GoogleAuthProvider.getCredential(account!!.idToken, null)
        auth.signInWithCredential(credetial)
            .addOnCompleteListener(requireActivity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    loginUserView.dataFromGoogle(user)
                    val intent = Intent(requireActivity, MainActivity::class.java)
                    requireActivity.startActivity(intent)
                } else {
                    loginUserView.failedLogin("Gagal Login")
                }
            }
    }
    override fun logOutGoogle() {
        var auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    override fun revokeAccess() {
        var auth = FirebaseAuth.getInstance()
        auth.signOut()

    }


}