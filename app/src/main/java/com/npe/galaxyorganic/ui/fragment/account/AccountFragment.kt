package com.npe.galaxyorganic.ui.fragment.account


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseAuth
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.MainActivity
import com.npe.galaxyorganic.ui.presenter.login.LoginPresenter
import com.npe.galaxyorganic.ui.view.LoginView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_account.view.*

class AccountFragment : Fragment(), LoginView.AccountUser{
    private lateinit var tvEmail : TextView
    private lateinit var tvNama : TextView
    private lateinit var btnPrivacyPolicy : Button
    private lateinit var btnHelp : Button
    private lateinit var btnlogout : Button
    private lateinit var imgProfile : CircleImageView
    private lateinit var auth : FirebaseAuth
    private lateinit var loginPresenter : LoginPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_account, container, false)

        tvEmail = v.tv_email_account
        tvNama = v.tv_nama_account
        btnPrivacyPolicy = v.btn_privacyPolicy_account
        btnHelp = v.btn_help_account
        btnlogout = v.btn_logout_account
        imgProfile = v.imgv_profile_account

        loginPresenter = LoginPresenter(this)
        auth = FirebaseAuth.getInstance()
        dataUser()

        btnlogout.setOnClickListener {
            loginPresenter.SignOut()
            loginPresenter.revokeAccess()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        return v
    }

    override fun dataUser() {
        val user = auth.currentUser
        tvEmail.text = user?.email
        tvNama.text = user?.displayName
        Glide.with(requireActivity())
            .load(user?.photoUrl)
            .into(imgProfile)
    }

}
