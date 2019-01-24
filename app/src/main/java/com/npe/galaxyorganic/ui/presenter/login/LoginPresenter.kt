package com.npe.galaxyorganic.ui.presenter.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.npe.galaxyorganic.ui.model.api.ApiRespository
import com.npe.galaxyorganic.ui.model.datum.DatumLoginModel
import com.npe.galaxyorganic.ui.model.root.RequestLoginModel
import com.npe.galaxyorganic.ui.model.root.RootLoginModel
import com.npe.galaxyorganic.ui.view.LoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter : LoginView.LoginGoogleView {
    var auth = FirebaseAuth.getInstance()
    lateinit var viewLogin: LoginView.LoginUserView
    lateinit var viewAccount: LoginView.AccountUser
    lateinit var mCallbackManager: CallbackManager

    companion object {
        lateinit var googleSignInClient: GoogleSignInClient
        lateinit var gso: GoogleSignInOptions
        var userDataDB: ArrayList<DatumLoginModel> = arrayListOf()
        lateinit var from: String
        lateinit var idUser: String
    }

    constructor(viewLogin: LoginView.LoginUserView) {
        this.viewLogin = viewLogin
    }

    constructor(viewAccount: LoginView.AccountUser) {
        this.viewAccount = viewAccount
    }


    override fun loginFrom(from: String) {
        LoginPresenter.from = from
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
                setLoginDB()
            } else {
                Log.w(TAG, "SignInWithCredentialFailure", task.exception)
                viewLogin.failedLogin(task.exception?.message.toString())
            }
            viewLogin.hideLoaidng()
        }
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
                viewLogin.failedLogin("Gagal Error")
            }

            override fun onError(error: FacebookException?) {
                viewLogin.failedLogin(error?.message)
            }

        })
    }

    override fun handleFacebookToken(
        accessToken: AccessToken?,
        activity: FragmentActivity?
    ) {
        val auth = FirebaseAuth.getInstance()
        val credentialFacebook: AuthCredential = FacebookAuthProvider.getCredential(accessToken!!.token)
        auth.signInWithCredential(credentialFacebook).addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    setLoginDB()
                } else {
                    viewLogin.failedLogin("Gagal Login Facebook")
                }
            }
        })
    }

    override fun setLoginDB() {
        val user = auth.currentUser
        val customers = ApiRespository.create()
        val requset = RequestLoginModel(user?.displayName.toString(), user?.email.toString())
        customers.getCustomers(requset).enqueue(object : Callback<RootLoginModel> {
            override fun onFailure(call: Call<RootLoginModel>, t: Throwable) {
                viewLogin.failedLogin("Gagal set DB")
            }

            override fun onResponse(call: Call<RootLoginModel>, response: Response<RootLoginModel>) {
                var dataResponse = response.body()
                if (dataResponse != null) {
                    if (dataResponse.api_message.equals("success")) {
                        userDataDB = dataResponse.data as ArrayList<DatumLoginModel>
                        getDataUserDB(userDataDB)
                    }
                }
            }

        })
    }

    override fun getDataUserDB(userDataDB: ArrayList<DatumLoginModel>) {
        viewLogin.successLogin(userDataDB.get(0).id!!)
    }

    override fun idUser(id: String) {
        LoginPresenter.idUser = id
    }

    override fun getIdUser(): Int {
        val idInt : Int = LoginPresenter.idUser.toInt()
        return idInt
    }

    override fun loginGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        viewLogin.startActivityForResult(signInIntent)
    }

    override fun SignOut() {
        if (from.equals("Facebook")) {
            auth.signOut()
        }
        if (from.equals("Google")) {
            auth.signOut()
            googleSignInClient.signOut().addOnCompleteListener {
            }
        }
    }

    override fun revokeAccess() {
        if (from.equals("Google")) {
            auth.signOut()
            googleSignInClient.revokeAccess().addOnCompleteListener {
            }
        }
    }
}
