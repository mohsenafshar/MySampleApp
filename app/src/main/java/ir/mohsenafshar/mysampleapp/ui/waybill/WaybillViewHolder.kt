package ir.mohsenafshar.mysampleapp.ui.waybill

import androidx.databinding.ViewDataBinding
import ir.mohsenafshar.mysampleapp.base.BaseViewHolder
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.mysampleapp.databinding.ItemWaybillBinding

class WaybillViewHolder(binding: ViewDataBinding) : BaseViewHolder<Waybill>(binding) {

    override fun onBindViewHolder(model: Waybill) {

        (binding as ItemWaybillBinding).apply {
            data = model
            executePendingBindings()

            root.setOnClickListener {
                clickCallback?.invoke(it, model)
            }
        }

    }

}
