package com.npe.galaxyorganic.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.fragment.LoginFirstFragment
import com.npe.galaxyorganic.ui.fragment.account.AccountFragment
import com.npe.galaxyorganic.ui.fragment.login.LoginFragment
import com.npe.galaxyorganic.ui.fragment.order.OrderFragment
import com.npe.galaxyorganic.ui.fragment.shop.ShopFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

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
                    val user = auth.currentUser
                    item.setCheckable(true)
                    if (user == null) {
                        loadLoginFirstFragment(savedInstanceState)
                    } else {
                        loadOrderFragment(savedInstanceState)
                    }
                }
                R.id.menu_login -> {
                    val user = auth.currentUser
                    item.setCheckable(true)
                    if (user == null) {
                        loadLoginFragment(savedInstanceState)
                    } else {
                        loadAccountFragment(savedInstanceState)
                    }
                }
            }
            true
        }


    }

    private fun loadLoginFirstFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, LoginFirstFragment(), LoginFirstFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadAccountFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
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

    override fun onStart() {
        super.onStart()
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            val menu: Menu = bottom_navigation.menu
            val menuAccount: MenuItem = menu.findItem(R.id.menu_login)
            menuAccount.title = "Profile"
        }
    }

    override fun onResume() {
        super.onResume()
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            val menu: Menu = bottom_navigation.menu
            val menuAccount: MenuItem = menu.findItem(R.id.menu_login)
            menuAccount.title = "Profile"
        }
    }
}
