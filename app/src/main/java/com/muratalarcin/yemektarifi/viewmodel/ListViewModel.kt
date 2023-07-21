package com.muratalarcin.yemektarifi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratalarcin.yemektarifi.model.Specification

class ListViewModel : ViewModel() {
    val specifications = MutableLiveData<List<Specification>>()
    val specificationError = MutableLiveData<Boolean>()
    val specificationLoading = MutableLiveData<Boolean>()

    fun refreshData() {
        val spec = Specification("pilav", "anayemek","pirinç", "prinç suda bekler zart zurt")
        val spec1 = Specification("kuru", "anayemek","fasulye", "prinç suda bekler zart zurt")
        val spec2 = Specification("musakka", "anayemek","patlıcan", "prinç suda bekler zart zurt")
        val spec3 = Specification("yoğurt", "eşlikçi","süt", "prinç suda bekler zart zurt")

        val specificationList = arrayListOf<Specification>(spec,spec1, spec2, spec3)

        specifications.value = specificationList
        specificationError.value = false
        specificationLoading.value = false
    }
}