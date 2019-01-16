package com.npe.galaxyorganic.ui.fragment.shop


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.npe.galaxyorganic.R
import com.npe.galaxyorganic.ui.model.ShopMenuModel
import kotlinx.android.synthetic.main.fragment_shop.view.*

class ShopFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_shop, container, false)

        v.recycler_menu_shop.layoutManager = GridLayoutManager(activity, 4)
        val data = ArrayList<ShopMenuModel>()
        setData(data)
        var adapter = AdapterShopFragment(requireContext(), data)
        v.recycler_menu_shop.adapter = adapter

        return v
    }

    private fun setData(data: ArrayList<ShopMenuModel>) {
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Kangkung"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Bayam"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Kentang"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Tomat"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Apel"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Alpukat"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Jeruk"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Kiwi"))
        data.add(ShopMenuModel(R.drawable.ic_shopping_cart, "Sawi"))
    }


}
