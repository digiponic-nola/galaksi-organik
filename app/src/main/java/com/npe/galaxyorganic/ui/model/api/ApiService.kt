package com.npe.galaxyorganic.ui.model.api

import com.npe.galaxyorganic.ui.model.RootCitiesModel
import com.npe.galaxyorganic.ui.model.RootShopDetailMenuModel
import com.npe.galaxyorganic.ui.model.RootShopItemModel
import com.npe.galaxyorganic.ui.model.RootShopMenuModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService{
    @GET("products")
    fun getListProducts() : Call<RootShopItemModel>

    @GET("cities")
    fun getListCities() : Call<RootCitiesModel>

    @GET("product_category?categories_id={categories_id}")
    fun getListProductCategory(@Path("categories_id") categories_id : Int) : Call<RootShopDetailMenuModel>

    @GET("categories")
    fun getListCategory(): Call<RootShopMenuModel>

}