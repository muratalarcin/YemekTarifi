package com.muratalarcin.yemektarifi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.muratalarcin.yemektarifi.model.Specification
import com.muratalarcin.yemektarifi.service.SpecificationDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {
    val specificationLiveData = MutableLiveData<Specification>()

    fun getDataFromRoom(uuid: Int) {

        launch {

            val dao = SpecificationDatabase(getApplication()).specificationDao()
            val specification = dao.getSpecification(uuid)
            specificationLiveData.value = specification

        }

    }
}