package ir.mohsenafshar.mysampleapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.myspotify.base.BaseViewModel
import javax.inject.Inject

class DetailViewModel
@Inject constructor() : BaseViewModel() {

    private val _waybillLiveData = MutableLiveData<Waybill>()
    val waybillLiveData: LiveData<Waybill>
        get() = _waybillLiveData

    fun setWaybill(waybill: Waybill) {
        _waybillLiveData.value = waybill
    }

}