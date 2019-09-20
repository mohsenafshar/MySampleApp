package ir.mohsenafshar.mysampleapp.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerFragment

abstract class BaseFragment<BINDING : ViewDataBinding> : DaggerFragment() {
    protected lateinit var binding: BINDING

    @JvmOverloads
    protected fun inflate(inflater: LayoutInflater, @LayoutRes layout: Int, container: ViewGroup, attachToParent: Boolean = false) {
        binding = DataBindingUtil.inflate(inflater, layout, container, attachToParent)
    }
}