package com.npe.galaxyorganic.ui.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.fragment.login.LoginFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        bottom_navigation.setOnNavigationItemSelectedListener { item->
            when(item.itemId){
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
                    loadLoginFragment(savedInstanceState)
                }
            }
            true
        }


    }

    private fun loadLoginFragment(savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame,LoginFragment(), LoginFragment::class.java.simpleName)
                .commit()
        }
    }

    private fun loadOrderFragment(savedInstanceState: Bundle?) {
        Toast.makeText(applicationContext, "order", Toast.LENGTH_SHORT).show()
    }

    private fun loadShopFragment(savedInstanceState: Bundle?) {
        Toast.makeText(applicationContext, "Shop", Toast.LENGTH_SHORT).show()
    }

}
