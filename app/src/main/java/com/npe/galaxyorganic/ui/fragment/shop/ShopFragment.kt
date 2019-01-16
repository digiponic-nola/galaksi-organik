package com.npe.galaxyorganic.ui.fragment.shop


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.ShopMenuModel
import com.npe.galaxyorganic.ui.presenter.ShopMenuPresenter
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.fragment_shop.view.*

class ShopFragment : Fragment(), ShopView.ShopMenuView {

    private lateinit var recycler : RecyclerView
    private lateinit var mAdapter : AdapterShopFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_shop, container, false)

        recycler = v.recycler_menu_shop
        val presenterMenu = ShopMenuPresenter(this, requireContext())
        presenterMenu.getDataMenu()

        return v
    }

    override fun dataMenu(data: List<ShopMenuModel>) {
        recycler.layoutManager = GridLayoutManager(activity, 4)
        mAdapter = AdapterShopFragment(requireContext(), data)
        recycler.adapter = mAdapter
    }




}
