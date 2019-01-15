package com.npe.galaxyorganic.ui.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.facebook.AccessToken
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.ui.fragment.account.AccountFragment
import com.npe.galaxyorganic.ui.ui.fragment.login.LoginFragment
import com.npe.galaxyorganic.ui.ui.fragment.order.OrderFragment
import com.npe.galaxyorganic.ui.ui.fragment.shop.ShopFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadShopFragment(savedInstanceState)

        bottom_navigation.menu.getItem(0).isCheckable = false

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_shop -> {
                    item.setCheckable(true)
                    loadShopFragment(savedInstanceState)
                }
                R.id.menu_order -> {
                    item.setCheckable(true)
                    loadOrderFragment(savedInstanceState)
                }
                R.id.menu_login -> {
                    item.setCheckable(true)
                    if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired) {
                        loadAccountFragment(savedInstanceState)
                    } else {
                        loadLoginFragment(savedInstanceState)
                    }
                }
            }
            true
        }


    }

    private fun loadAccountFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, AccountFragment(), AccountFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadLoginFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, LoginFragment(), LoginFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadOrderFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, OrderFragment(), OrderFragment::class.java.simpleName)
                .commit()
        } else {
            Toast.makeText(applicationContext, "Login First", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadShopFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, ShopFragment(), ShopFragment::class.java.simpleName)
                .commit()
        } else {
            Toast.makeText(applicationContext, "Shop", Toast.LENGTH_SHORT).show()
        }
    }

}
