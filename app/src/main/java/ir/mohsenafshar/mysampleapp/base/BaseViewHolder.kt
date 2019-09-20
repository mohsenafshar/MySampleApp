package ir.mohsenafshar.mysampleapp.base


import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<MODEL>(val b: ViewDataBinding) : RecyclerView.ViewHolder(b.root) {

    var clickCallback: ClickCallback<MODEL>? = null
    var longClickCallback: LongClickCallback<MODEL>? = null
    var binding = b

    abstract fun onBindViewHolder(model: MODEL)

}