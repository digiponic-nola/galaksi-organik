package com.npe.galaxyorganic.ui.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.FacebookSdk
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.fragment.login.LoginFragment
import com.npe.galaxyorganic.ui.presenter.login.LoginPresenter
import com.npe.galaxyorganic.ui.view.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity(), LoginView.LoginUserView  {

    private lateinit var loginPresenter: LoginPresenter

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        loginPresenter = LoginPresenter(this)

        if (auth.currentUser == null) {
            FacebookSdk.sdkInitialize(applicationContext)
            //login_button_facebook_activity
        }

        btn_login_facebook_activity.setOnClickListener {
            loginPresenter.loginFrom("Facebook")
            loginPresenter.initFB()
            login_button_facebook_activity.setReadPermissions(Arrays.asList("email"))
            //login_button_facebook_activity.setFragment(this)
            login_button_facebook_activity.performClick()
            loginPresenter.onButtonClicked(this)
        }

        btn_login_google_activity.setOnClickListener {
            loginPresenter.loginFrom("Google")
            loginPresenter.configureGoogle(getString(R.string.default_web_client_id),this)
            loginPresenter.loginGoogle()
        }

    }

    override fun showLoading() {
    }

    override fun hideLoaidng() {
    }

    override fun successLogin(idUser: Int) {
        val id = idUser.toString()
        val user = auth.currentUser
        loginPresenter.idUser(id)
        loginPresenter.addToCustomerModel(applicationContext,user, id)
        Toast.makeText(applicationContext, "Selamat Datang " + auth.currentUser?.displayName, Toast.LENGTH_SHORT).show()
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    override fun failedLogin(message: String?) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun startActivityForResult(signInIntent: Intent) {
        startActivityForResult(signInIntent, LoginActivity.RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode != LoginActivity.RC_SIGN_IN) {
            loginPresenter.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == LoginActivity.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Log.d("LOGIN_GOOGLE", "MASUK")
                val account = task.getResult(ApiException::class.java)
                loginPresenter.firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                failedLogin(e.message)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (auth.currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
