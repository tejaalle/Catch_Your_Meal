package com.group4.catchyourmeal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
//import com.group4.catchyourmeal.database.carttable
//import com.group4.catchyourmeal.database.mealdatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
//class ShoppingCartViewModel : ViewModel(){
//    protected val compositeDisposable = CompositeDisposable()
//
//    private var dataBaseInstance: mealdatabase ?= null
//
//    var personsList = MutableLiveData<List<carttable>>()
//
//    fun setInstanceOfDb(dataBaseInstance: mealdatabase) {
//        this.dataBaseInstance = dataBaseInstance
//    }
//
//    fun saveDataIntoDb(data: carttable){
//
//        dataBaseInstance?.cartDao?.insert(data)
//            ?.subscribe(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe ({
//            },{
//
//            })?.let {
//                compositeDisposable.add(it)
//            }
//    }
//
//    fun getPersonData(){
//
//        dataBaseInstance?.cartDao()?.get()
//            ?.subscribeOn(Schedulers.io())
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe ({
//                if(!it.isNullOrEmpty()){
//                    personsList.postValue(it)
//                }else{
//                    personsList.postValue(listOf())
//                }
//                it?.forEach {
//                    Log.v("Person Name",it.nameFUll)
//                }
//            },{
//            })?.let {
//                compositeDisposable.add(it)
//            }
//    }
//
//}
class ShoppingCartViewModel(application: Application) : AndroidViewModel(application) {
    private var job: Job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Main)
    private val _text = MutableLiveData<String>().apply {
        value = "This is favorites Fragment"
    }
    val text: LiveData<String> = _text
    private var favList=MutableLiveData<List<carttable>>()
    private var _favList=MutableLiveData<List<carttable>>()

//    init {
//        var db = mealdatabase.getInstance(application.applicationContext)
//        var cartDao=db.cartDao
//        scope.launch {
//            _favList=MutableLiveData(cartDao.getAll())
//            favList=_favList
//        }
  //  }
}
