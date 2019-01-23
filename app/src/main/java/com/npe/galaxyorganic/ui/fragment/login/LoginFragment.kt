package com.npe.galaxyorganic.ui.fragment.login


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.facebook.FacebookSdk
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.presenter.login.LoginPresenter
import com.npe.galaxyorganic.ui.view.LoginView
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.*


class LoginFragment : Fragment(), LoginView.LoginUserView {
    private lateinit var btnFacebook: Button
    private lateinit var btnGoogle: Button
    private lateinit var loginButtonFB: LoginButton

    private lateinit var loginPresenter: LoginPresenter

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()
        loginPresenter = LoginPresenter(this)
        //facebook setting
        btnFacebook = v.btn_login_facebook
        if (auth.currentUser == null) {
            FacebookSdk.sdkInitialize(context)
            loginButtonFB = v.login_button_facebook
        }
        btnFacebook.setOnClickListener {
            loginPresenter.loginFrom("Facebook")
            loginPresenter.initFB()
            loginButtonFB.setReadPermissions(Arrays.asList("email"))
            loginButtonFB.setFragment(this)
            loginButtonFB.performClick()
            loginPresenter.onButtonClicked(activity)
        }


        //google setting
        btnGoogle = v.btn_login_google
        btnGoogle.setOnClickListener {
            loginPresenter.loginFrom("Google")
            loginPresenter.configureGoogle(getString(R.string.default_web_client_id), requireActivity())
            loginPresenter.loginGoogle()
        }


        return v
    }

    override fun startActivityForResult(signInIntent: Intent) {
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode != RC_SIGN_IN) {
            loginPresenter.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == RC_SIGN_IN) {
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

    override fun showLoading() {
    }

    override fun hideLoaidng() {

    }

    override fun successLogin() {
        Toast.makeText(context, "Selamat Datang " + auth.currentUser?.displayName, Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    override fun failedLogin(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        if (auth.currentUser != null) {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
