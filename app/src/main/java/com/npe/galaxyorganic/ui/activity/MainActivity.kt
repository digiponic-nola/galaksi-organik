package com.npe.galaxyorganic.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import at.markushi.ui.CircleButton
import com.google.firebase.auth.FirebaseAuth
import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.activity.shop.BottomSheetRVAdapter
import com.npe.galaxyorganic.ui.fragment.LoginFirstFragment
import com.npe.galaxyorganic.ui.fragment.account.AccountFragment
import com.npe.galaxyorganic.ui.fragment.login.LoginFragment
import com.npe.galaxyorganic.ui.fragment.order.OrderFragment
import com.npe.galaxyorganic.ui.fragment.shop.ShopFragment
import com.npe.galaxyorganic.ui.model.datum.DatumItemOrder
import com.npe.galaxyorganic.ui.utils.gone
import com.npe.galaxyorganic.ui.utils.visible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sheetBehavior: BottomSheetBehavior<RelativeLayout>
    private lateinit var recyclerItem: RecyclerView
    private lateinit var mAdapterItem: BottomSheetRVAdapter
    private var data: ArrayList<DatumItemOrder> = arrayListOf()
    private lateinit var btnCheckout: CircleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadShopFragment(savedInstanceState)

        bottom_navigation.menu.getItem(0).isCheckable = false

        sheetBehavior = BottomSheetBehavior.from<RelativeLayout>(bottom_sheet)

        peek_layout.setOnClickListener {
//            val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
//            val dialog = BottomSheetDialog(this)
//            dialog.setContentView(view)
//            dialog.show()

                BottomSheetBehavior.from<RelativeLayout>(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED

        }
        recyclerItem = rv_item_bottomSheet
        btnCheckout = floatBtn_checkout

        recyclerItem.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.VERTICAL, false)

        data.add(DatumItemOrder(1, "Buncis","11000", "1"))
        mAdapterItem = BottomSheetRVAdapter(this@MainActivity, data)
        recyclerItem.adapter = mAdapterItem

        floatBtn_checkout.setOnClickListener{
            expandCloseSheet()
        }

        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        btnCheckout.visible()
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        btnCheckout.gone()
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        btnCheckout.gone()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        btnCheckout.gone()
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        btnCheckout.gone()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })

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

    private fun expandCloseSheet() {
        if (sheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        } else {
            sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
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
