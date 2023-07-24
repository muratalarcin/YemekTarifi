package com.muratalarcin.yemektarifi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.muratalarcin.yemektarifi.model.Specification
import io.reactivex.schedulers.Schedulers
import com.muratalarcin.yemektarifi.service.SpecificationAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver


class ListViewModel : ViewModel() {
    private val specificationAPIService = SpecificationAPIService()
    private val disposable = CompositeDisposable()

    val specifications = MutableLiveData<List<Specification>>()
    val specificationError = MutableLiveData<Boolean>()
    val specificationLoading = MutableLiveData<Boolean>()

    // Verileri API'den almak için bu fonksiyonu çağırın
    fun refreshData() {
        getDataFromAPI()
    }

    // API'den verileri almak için bu özel fonksiyonu kullanın
    private fun getDataFromAPI() {
        specificationLoading.value = true

        disposable.add(
            specificationAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Specification>>() {
                    override fun onSuccess(t: List<Specification>) {
                        // API'den gelen verileri LiveData'ya atayın
                        specifications.value = t
                        specificationError.value = false
                        specificationLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        // Hata durumunda hata mesajını ekrana yazdırın
                        specificationLoading.value = false
                        specificationError.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    // ViewModel yok edildiğinde, disposable nesnesini temizleyin
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
