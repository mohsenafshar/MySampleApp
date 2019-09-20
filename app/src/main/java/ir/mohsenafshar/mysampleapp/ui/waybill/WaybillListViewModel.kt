package ir.mohsenafshar.mysampleapp.ui.waybill

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ir.mohsenafshar.mysampleapp.data.DataSource
import ir.mohsenafshar.mysampleapp.data.Repository
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.myspotify.base.BaseViewModel
import javax.inject.Inject

class WaybillListViewModel
@Inject constructor(
    private val repository: Repository
) : BaseViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _items = MutableLiveData<ArrayList<Waybill>>().apply { value = ArrayList() }
    val items: LiveData<ArrayList<Waybill>>
        get() = _items

    fun getWaybills(pageNo : Int) {
        repository.getConsignmentList(pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _dataLoading.value = true
            }
            .doFinally {
                _dataLoading.value = false
            }
            .subscribe ({
                _items.value = _items.value.apply {
                    this?.addAll(it)
                }

            },{
                _error.value = it
            }).addAsDisposable()
    }

}