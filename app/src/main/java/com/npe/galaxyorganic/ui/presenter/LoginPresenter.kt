package com.npe.galaxyorganic.ui.presenter

import android.content.Intent
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.npe.galaxyorganic.ui.view.LoginView
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL

class LoginPresenter : LoginView.LoginFacebookView{
    private lateinit var mCallbackManager : CallbackManager

    override fun initFB() {
        mCallbackManager = CallbackManager.Factory.create()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun logIn(btnFacebook: LoginButton) {
        btnFacebook.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                // App code
                var accessToken : String = loginResult.accessToken.token

                var request : GraphRequest = GraphRequest.newMeRequest(loginResult.accessToken, object : GraphRequest.GraphJSONObjectCallback{
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

}