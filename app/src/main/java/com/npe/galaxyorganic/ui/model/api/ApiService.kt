package com.npe.galaxyorganic.ui.model.api

import com.npe.galaxyorganic.ui.model.root.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService{
    @GET("products")
    fun getListProducts() : Call<RootShopItemModel>

    @GET("cities")
    fun getListCities() : Call<RootCitiesModel>

    @GET("product_category")
    fun getListProductCategory(@Query("categories_id") categories_id : Int) : Call<RootShopDetailMenuModel>

    @GET("categories")
    fun getListCategory(): Call<RootShopMenuModel>

    @POST("customers")
    fun getCustomers(@Body requestLogin : RequestLoginModel) : Call<RootLoginModel>

    @GET("districs")
    fun getListDistrik(@Query("cities_id") cities_id : Int) : Call<RootDistrikModel>

    @POST("orders")
    fun sendOrder(@Body requestOrder : RequestOrderModel) : Call<RootOrderModel>
}