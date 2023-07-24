package com.muratalarcin.yemektarifi.service

import com.muratalarcin.yemektarifi.model.Specification
import io.reactivex.Single
import retrofit2.http.GET

interface SpecificationAPI {
    //GET - POST
    //https://raw.githubusercontent.com/muratalarcin/Json/master/yemekTarifi.json

    @GET("muratalarcin/Json/master/yemekTarifi.json")
    fun getSpecifications(): Single<List<Specification>>

}