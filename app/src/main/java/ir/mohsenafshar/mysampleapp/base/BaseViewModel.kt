package ir.mohsenafshar.myspotify.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    protected val _error = MutableLiveData<Throwable>()
    val error: LiveData<Throwable>
        get() = _error

    protected val _failure = MutableLiveData<String>()
    val failure: LiveData<String>
        get() = _failure



    protected fun Disposable.addAsDisposable() {
        compositeDisposable = compositeDisposable ?: CompositeDisposable()
        compositeDisposable!!.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.clear()
    }
}