package com.npe.galaxyorganic.ui.model.api

import com.npe.galaxyorganic.ui.model.root.RootCitiesModel
import com.npe.galaxyorganic.ui.model.root.RootShopDetailMenuModel
import com.npe.galaxyorganic.ui.model.root.RootShopItemModel
import com.npe.galaxyorganic.ui.model.root.RootShopMenuModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService{
    @GET("products")
    fun getListProducts() : Call<RootShopItemModel>

    @GET("cities")
    fun getListCities() : Call<RootCitiesModel>

    @GET("product_category")
    fun getListProductCategory(@Query("categories_id") categories_id : Int) : Call<RootShopDetailMenuModel>

    @GET("categories")
    fun getListCategory(): Call<RootShopMenuModel>

}