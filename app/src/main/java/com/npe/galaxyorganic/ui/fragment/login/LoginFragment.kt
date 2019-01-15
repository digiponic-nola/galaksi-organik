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
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.json.JSONException
import java.util.*
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL


class LoginFragment : Fragment() {

    private lateinit var callbackManager : CallbackManager
    private lateinit var btnFacebook : LoginButton
    private lateinit var btnGoogle : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_login, container, false)

        btnFacebook = v.btn_login_facebook
        btnGoogle = v.btn_login_google

        callbackManager = CallbackManager.Factory.create()

        btnFacebook.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"))

        btnFacebook.setFragment(this)

        btnFacebook.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                var accessToken : String = loginResult.accessToken.token

                var request :GraphRequest = GraphRequest.newMeRequest(loginResult.accessToken, object : GraphRequest.GraphJSONObjectCallback{
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

        btnGoogle.setOnClickListener {
            loginGoogle()
        }

        return v
    }

    private fun getDataObject(jsonObject: JSONObject?) {
        try {
            var profile_pict : URL = URL("http://graph.facebook.com/"+jsonObject?.getString("id")+"/picture?width=250&height=250")
        } catch(e : MalformedURLException){
            e.printStackTrace()
        } catch(e : JSONException){
            e.printStackTrace()
        }
    }

    private fun loginGoogle() {
        Toast.makeText(context, "Google", Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
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
