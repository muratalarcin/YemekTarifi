package com.muratalarcin.yemektarifi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.muratalarcin.yemektarifi.model.Specification
import io.reactivex.schedulers.Schedulers
import com.muratalarcin.yemektarifi.service.SpecificationAPIService
import com.muratalarcin.yemektarifi.service.SpecificationDatabase
import com.muratalarcin.yemektarifi.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import kotlinx.coroutines.launch


class ListViewModel(application: Application) : BaseViewModel(application) {
    private val specificationAPIService = SpecificationAPIService()
    private val disposable = CompositeDisposable()
    private val customPreferences = CustomSharedPreferences(getApplication())
    private val refreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val specifications = MutableLiveData<List<Specification>>()
    val specificationError = MutableLiveData<Boolean>()
    val specificationLoading = MutableLiveData<Boolean>()
    val searchResult = MutableLiveData<List<Specification>?>()

    // Verileri API'den almak için bu fonksiyonu çağırın
    fun refreshData() {

        val updateTime = customPreferences.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime) {
            getDataFromSQLite()
        } else {
            getDataFromAPI()
        }
    }

    fun refreshDataFromAPI() {
        getDataFromAPI()
    }

    private fun getDataFromSQLite() {
        specificationLoading.value = true
        launch {
            val specifications =
                SpecificationDatabase(getApplication()).specificationDao().getAllSpecifications()
            showSpecifications(specifications)
            //Toast.makeText(getApplication(), "Specifications From SQLite", Toast.LENGTH_SHORT).show()
        }
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
                        storeInSQLite(t)
                        //Toast.makeText(getApplication(), "Specifications From API", Toast.LENGTH_SHORT).show()
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

    private fun showSpecifications(specificationList: List<Specification>) {
        specifications.value = specificationList
        specificationError.value = false
        specificationLoading.value = false
    }

    private fun storeInSQLite(list: List<Specification>) {
        launch {
            val dao = SpecificationDatabase(getApplication()).specificationDao()
            dao.deleteAllSpecifications()
            val listLong = dao.insertAll(*list.toTypedArray()) //-> * koyunca başına, dizi halinden tekli hale getiriyor, individual
            var i = 0
            while (i < listLong.size) {
                list[i].uuid = listLong[i].toInt()
                i += 1
            }

            showSpecifications(list)

        }

        customPreferences.saveTime(System.nanoTime())

    }

    fun searchSpecifications(query: String) {
        launch {
            // specifications listesini query'ye göre filtreleyip searchResult LiveData'sına atayın.
            // Burada, specicationName alanındaki değeri query'ye göre filtreleyebilirsiniz.
            val filteredList = specifications.value?.filter { specification ->
                specification.specificationName!!.contains(query, ignoreCase = true) || specification.specificationTag!!.contains(query, ignoreCase = true)
            }
            searchResult.value = filteredList
        }
    }

    // ViewModel yok edildiğinde, disposable nesnesini temizleyin
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}
