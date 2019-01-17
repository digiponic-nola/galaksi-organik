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
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.presenter.login.LoginGooglePresenter
import com.npe.galaxyorganic.ui.view.LoginView
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.*


class LoginFragment : Fragment(), LoginView.LoginUserView {


    private lateinit var btnFacebook: LoginButton
    private lateinit var btnGoogle: Button

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googlePresenter: LoginGooglePresenter

    private lateinit var auth: FirebaseAuth
    private lateinit var mCallbackManager: CallbackManager

    companion object {
        lateinit var gso: GoogleSignInOptions
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

        if (auth.currentUser == null) {
            FacebookSdk.sdkInitialize(context)
            btnFacebook = v.btn_login_facebook
        }

        btnGoogle = v.btn_login_google

        mCallbackManager = CallbackManager.Factory.create()
        btnFacebook.setReadPermissions(Arrays.asList("email"))
        btnFacebook.setFragment(this)
        //facebook


        btnFacebook.setOnClickListener { v ->
            buttonClickLoginFB(v)
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

    fun buttonClickLoginFB(view: View) {
        LoginManager.getInstance().registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookToken(result?.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(context, "User Canceled", Toast.LENGTH_SHORT).show()
            }

            override fun onError(error: FacebookException?) {
                Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun handleFacebookToken(accessToken: AccessToken?) {
        val credentialFacebook: AuthCredential = FacebookAuthProvider.getCredential(accessToken!!.token)
        auth.signInWithCredential(credentialFacebook).addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    val currentUser = auth.currentUser
                    dataUser(currentUser)
                } else {
                    Toast.makeText(context, "error login", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun dataUser(currentUser: FirebaseUser?) {
        Log.d("EmailFacebook", currentUser?.email)
    }

    override fun loginGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                googlePresenter.firebaseAuthWithGoogle(account, requireActivity())
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
    }

    override fun dataFromGoogle(user: FirebaseUser?) {
        if (user != null) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("Nama", user.displayName)
            startActivity(intent)
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

        //google
        if (auth.currentUser != null) {
            dataFromGoogle(auth.currentUser)
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        if(auth.currentUser != null){
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
