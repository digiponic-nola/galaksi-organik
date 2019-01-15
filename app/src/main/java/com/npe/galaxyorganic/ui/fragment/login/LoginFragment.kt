package com.npe.galaxyorganic.ui.fragment.login


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.facebook.*
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.presenter.LoginFacebookPresenter
import com.npe.galaxyorganic.ui.presenter.LoginGooglePresenter
import com.npe.galaxyorganic.ui.view.LoginView
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.*


class LoginFragment : Fragment() , LoginView.LoginUserView {


    private lateinit var btnFacebook : LoginButton
    private lateinit var btnGoogle : Button
    private lateinit var facebookPresenterFacebook : LoginFacebookPresenter

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googlePresenter : LoginGooglePresenter

    companion object {
        lateinit var gso : GoogleSignInOptions
        private const val TAG = "GoogleActivity"
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        btnFacebook = v.btn_login_facebook
        btnGoogle = v.btn_login_google

        //facebook setting
        facebookPresenterFacebook = LoginFacebookPresenter()
        facebookPresenterFacebook.initFB()
        btnFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"))
        btnFacebook.setFragment(this)
        btnFacebook.setOnClickListener {
            facebookPresenterFacebook.logIn(btnFacebook)
        }

        //google setting
        googlePresenter = LoginGooglePresenter(this)
        googlePresenter.configureGoogle(getString(R.string.default_web_client_id))

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        btnGoogle.setOnClickListener {
            loginGoogle()
        }


        return v
    }

    override fun loginGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            facebookPresenterFacebook.onActivityResult(requestCode, resultCode, data)
        }
        if(requestCode == RC_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                googlePresenter.firebaseAuthWithGoogle(account, requireActivity())
            } catch (e : ApiException){
                e.printStackTrace()
            }
        }
    }

    override fun dataFromGoogle(user: FirebaseUser?) {
        if(user != null){
          Toast.makeText(context, "Email:"+user.email, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "user:null", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showLoading() {

    }

    override fun hideLoaidng() {

    }

    override fun onStart() {
        super.onStart()
        //facebook
        if(AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired){
            Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
        //google
        var auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        dataFromGoogle(currentUser)
    }

    override fun onResume() {
        super.onResume()
        if(AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired){
            Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
