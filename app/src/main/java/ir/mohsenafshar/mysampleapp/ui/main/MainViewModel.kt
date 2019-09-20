package ir.mohsenafshar.mysampleapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.myspotify.base.BaseViewModel
import javax.inject.Inject

class MainViewModel
@Inject constructor() : BaseViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _headerVisible = MutableLiveData<Boolean>(false)
    val headerVisible: LiveData<Boolean>
        get() = _headerVisible


    fun setTitle(s: String) {
        _title.value = s
    }

    fun setHeaderVisibility(b: Boolean) {
        _headerVisible.value = b
    }

}