package com.muratalarcin.yemektarifi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratalarcin.yemektarifi.model.Specification

class DetailViewModel : ViewModel() {
    val specificationLiveData = MutableLiveData<Specification>()

    fun getDataFromRoom() {
        val spec= Specification("pilav", "ana yemek", "pirinç", "haşlama pişirme")
        specificationLiveData.value = spec

    }
}