package com.npe.galaxyorganic.ui.model.api

import com.npe.galaxyorganic.ui.model.DatumCitiesModel
import com.npe.galaxyorganic.ui.model.RootCitiesModel
import com.npe.galaxyorganic.ui.model.RootShopItemModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiService{
    @GET("products")
    fun getListProducts() : Call<RootShopItemModel>

    @GET("cities")
    fun getListCities() : Call<RootCitiesModel>

}