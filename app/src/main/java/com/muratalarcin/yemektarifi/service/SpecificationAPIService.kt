package com.muratalarcin.yemektarifi.service

import com.muratalarcin.yemektarifi.model.Specification
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SpecificationAPIService {

    //https://raw.githubusercontent.com/muratalarcin/Json/master/yemekTarifi.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SpecificationAPI::class.java)

    fun getData(): Single<List<Specification>> {
        return api.getSpecifications()
    }
}