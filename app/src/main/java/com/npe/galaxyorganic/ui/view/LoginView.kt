package com.npe.galaxyorganic.ui.view

import android.content.Context
import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import com.npe.galaxyorganic.ui.model.datum.DatumLoginModel
import com.npe.galaxyorganic.ui.model.db.CustomerModel

interface LoginView {
    interface LoginUserView {
        fun showLoading()
        fun hideLoaidng()
        fun successLogin(idUser: Int)
        fun failedLogin(message: String?)
        fun startActivityForResult(signInIntent: Intent)
    }

    interface AccountUser {
        fun dataUser(customerData: MutableList<CustomerModel>)
    }

    interface LoginGoogleView {
        fun loginFrom(from : String)
        fun setLoginDB()
        fun getDataUserDB(userDataDB: ArrayList<DatumLoginModel>)
        fun idUser(id: String)
        fun getIdUser(): Int
        //anko sql
        fun addToCustomerModel(
            context: Context?,
            user: FirebaseUser?,
            id: String
        )
        fun showDataDB(context : Context)
        fun removeDataCustomerDB(context : Context, id : String)
        //facebook
        fun initFB()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
        fun onButtonClicked(activity: FragmentActivity?)
        fun handleFacebookToken(
            accessToken: AccessToken?,
            activity: FragmentActivity?
        )
        //google
        fun configureGoogle(
            default_web_client_id: String,
            requireActivity: FragmentActivity
        )
        fun firebaseAuthWithGoogle(account: GoogleSignInAccount?)
        fun loginGoogle()
        fun SignOut(context: Context, idSQLcustomer: String, login_from : String)
        fun revokeAccess(login_from : String)
    }
}