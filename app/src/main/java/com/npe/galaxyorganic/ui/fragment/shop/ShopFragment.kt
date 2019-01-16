package com.npe.galaxyorganic.ui.fragment.shop


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.ShopItemModel
import com.npe.galaxyorganic.ui.model.ShopMenuModel
import com.npe.galaxyorganic.ui.presenter.shop.ShopItemPresenter
import com.npe.galaxyorganic.ui.presenter.shop.ShopMenuPresenter
import com.npe.galaxyorganic.ui.view.ShopView
import kotlinx.android.synthetic.main.fragment_shop.view.*

class ShopFragment : Fragment(), ShopView.ShopMenuView, ShopView.ShopItemView {

    private lateinit var recyclerMenu : RecyclerView
    private lateinit var recyclerItem : RecyclerView
    private lateinit var mAdapterMenu : AdapterShopFragment
    private lateinit var mAdapterItem : AdapterShopItemFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_shop, container, false)

        recyclerMenu = v.recycler_menu_shop
        recyclerItem = v.recycler_all_list_shop
        val presenterItem = ShopItemPresenter(this, requireContext())
        val presenterMenu = ShopMenuPresenter(this, requireContext())
        presenterMenu.getDataMenu()

        presenterItem.getAllItem()

        return v
    }

    override fun dataMenu(data: List<ShopMenuModel>) {
        recyclerMenu.layoutManager = GridLayoutManager(activity, 4)
        mAdapterMenu = AdapterShopFragment(requireContext(), data)
        recyclerMenu.adapter = mAdapterMenu
    }

    override fun dataItem(data: List<ShopItemModel>) {
        recyclerItem.layoutManager = GridLayoutManager(activity,2)
        mAdapterItem = AdapterShopItemFragment(requireContext(), data)
        recyclerItem.adapter = mAdapterItem
    }

}
