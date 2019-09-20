package ir.mohsenafshar.mysampleapp.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.InvocationTargetException

typealias ClickCallback<MODEL> = (view: View, model: MODEL) -> Unit
typealias LongClickCallback<MODEL> = (view: View, model: MODEL) -> Boolean

class BaseAdapter<MODEL, VH : BaseViewHolder<MODEL>> private constructor(
    @LayoutRes
    private val layoutId: Int,
    private val itemList: List<MODEL>,
    private val vhClass: Class<VH>,
    private val clickCallback: ClickCallback<MODEL>?,
    private val longClickCallback: LongClickCallback<MODEL>?
) : RecyclerView.Adapter<BaseViewHolder<MODEL>>() {

    private constructor(builder: Builder<MODEL, VH>) : this(
        builder.layoutId,
        builder.itemList,
        builder.vhClass,
        builder.clickCallback,
        builder.longClickCallback
    )

    companion object {
        inline fun <model, vh : BaseViewHolder<model>> build(
            @LayoutRes
            layoutId: Int,
            itemList: List<model>,
            vhClass: Class<vh>,
            block: Builder<model, vh>.() -> Unit
        ) =
            Builder(layoutId, itemList, vhClass).apply(block).build()

        fun <model, vh : BaseViewHolder<model>> build(
            @LayoutRes
            layoutId: Int,
            itemList: List<model>,
            vhClass: Class<vh>
        ) =
            Builder(layoutId, itemList, vhClass).build()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<MODEL> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )

        var vh: VH? = null
        try {
            vh = vhClass.getConstructor(ViewDataBinding::class.java).newInstance(binding)
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }

        return if (vh == null) {
            throw NullPointerException()
        } else {
            vh
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<MODEL>, position: Int) {
        holder.onBindViewHolder(itemList[position])

        clickCallback?.run {
            holder.clickCallback = this
        }

        longClickCallback?.run {
            holder.longClickCallback = this
        }
    }


    class Builder<Model, Vh : BaseViewHolder<Model>>(
        @LayoutRes
        val layoutId: Int,
        val itemList: List<Model>,
        val vhClass: Class<Vh>
    ) {

        var clickCallback: ClickCallback<Model>? = null
        var longClickCallback: LongClickCallback<Model>? = null

        fun build() = BaseAdapter(this)

//        fun layoutId(@LayoutRes layoutId: Int) = apply { this.layoutId = layoutId }
//        fun itemList(itemList: List<model>) = apply { this.itemList = itemList }
//        fun viewHolderClass(vhClass: Class<vh>) = apply { this.vhClass = vhClass }
//        fun clickCallback(clickCallback: ClickCallback<model>) = apply { this.clickCallback = clickCallback }
//        fun longClickCallback(longClickCallback: LongClickCallback<model>) = apply { this.longClickCallback = longClickCallback }
    }
}