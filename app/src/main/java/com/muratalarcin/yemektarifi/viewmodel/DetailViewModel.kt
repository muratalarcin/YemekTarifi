package com.muratalarcin.yemektarifi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratalarcin.yemektarifi.model.Specification

class DetailViewModel : ViewModel() {
    val specificationLiveData = MutableLiveData<Specification>()

    fun getDataFromRoom() {
        val spec= Specification("pilav", "ana yemek", "pirinç", "haşlama pişirme", "https://www.google.com/url?sa=i&url=https%3A%2F%2Fcooking.nytimes.com%2Frecipes%2F1020640-beef-wellington&psig=AOvVaw06RsG2AQsKLIZq-VDl2iZv&ust=1690279608202000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNi24a6Mp4ADFQAAAAAdAAAAABAE")
        specificationLiveData.value = spec

    }
}