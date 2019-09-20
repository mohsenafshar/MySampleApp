package ir.mohsenafshar.mysampleapp.ui.waybill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.DaggerFragment
import ir.mohsenafshar.mysampleapp.R
import ir.mohsenafshar.mysampleapp.base.BaseAdapter
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.mysampleapp.data.remote.RemoteDataProvider
import ir.mohsenafshar.mysampleapp.databinding.FragmentWaybillBinding
import ir.mohsenafshar.mysampleapp.ui.main.MainViewModel
import ir.mohsenafshar.mysampleapp.util.EndlessRecyclerOnScrollListener
import timber.log.Timber
import javax.inject.Inject

class WaybillListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<WaybillListViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentWaybillBinding

    private lateinit var listAdapter: BaseAdapter<Waybill, WaybillViewHolder>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentWaybillBinding.inflate(inflater, container, false).apply {
            vm = viewModel
        }

        return viewDataBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getWaybills(0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupListAdapter()

        viewModel.items.observe(viewLifecycleOwner, Observer {
            Timber.d(it.toString())
            Toast.makeText(context, "Data Received", Toast.LENGTH_LONG).show()

            listAdapter.notifyDataSetChanged()
        })
    }

    private fun setupListAdapter() {
        listAdapter =
            BaseAdapter.build(
                R.layout.item_waybill,
                viewModel.items.value!!,
                WaybillViewHolder::class.java
            ) {
                clickCallback = { view, model ->
                    navigateToAddNewTask(model)
                }
            }

        viewDataBinding.rcWaybill.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context).also {
                addOnScrollListener(object : EndlessRecyclerOnScrollListener(it) {
                    override fun onLoadMore(currentPage: Int) {
                        viewModel.getWaybills(currentPage)
                    }
                })
            }

        }
    }


    private fun navigateToAddNewTask(waybill: Waybill) {
        val action = WaybillListFragmentDirections
            .actionWaybillListFragmentToDetailFragment(waybill)
        findNavController().navigate(action)
    }

}