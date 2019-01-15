package com.npe.galaxyorganic.ui.ui.fragment.login


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
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.presenter.LoginFacebookPresenter
import com.npe.galaxyorganic.ui.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.view.LoginUserView
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL
import java.util.*


class LoginFragment : Fragment(), LoginUserView.LoginView {
    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var callbackManager: CallbackManager
    private lateinit var btnFacebook: LoginButton
    private lateinit var btnGoogle: Button
    private final var GOOGLE_SIGN: Int = 123
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var presenterLoginFacebook : LoginFacebookPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        btnFacebook = v.btn_login_facebook
        btnGoogle = v.btn_login_google
        mAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        btnFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"))

        btnFacebook.setFragment(this)

        btnFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                var accessToken: String = loginResult.accessToken.token

                var request: GraphRequest =
                    GraphRequest.newMeRequest(loginResult.accessToken, object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
                            getDataObject(`object`)
                        }

                    })
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)


        btnGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN)
        }

        return v
    }

    private fun getDataObject(jsonObject: JSONObject?) {
        try {
            var profile_pict: URL =
                URL("http://graph.facebook.com/" + jsonObject?.getString("id") + "/picture?width=250&height=250")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                var account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + account?.id)
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()){task ->
                if(task.isSuccessful){
                    val user = mAuth.currentUser
                } else {
                    
                }
            }
    }


    override fun onResume() {
        super.onResume()
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired) {
            Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }
    }


}
