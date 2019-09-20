package ir.mohsenafshar.mysampleapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.DaggerFragment
import ir.mohsenafshar.mysampleapp.databinding.FragmentDetailBinding
import ir.mohsenafshar.mysampleapp.databinding.FragmentWaybillBinding
import ir.mohsenafshar.mysampleapp.ui.main.MainViewModel
import timber.log.Timber
import javax.inject.Inject

class DetailFragment : DaggerFragment() {

    private val args: DetailFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<DetailViewModel> { viewModelFactory }

    private val mainViewModel by activityViewModels<MainViewModel> { viewModelFactory }

    private lateinit var viewDataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentDetailBinding.inflate(inflater, container, false).apply {
            vm = viewModel
        }
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setWaybill(args.waybill)

        viewDataBinding.btnSubmit.clicks()
            .subscribe({
                mainViewModel.setTitle("${args.waybill.origin} به ${args.waybill.destination} ${args.waybill.consignment} ${args.waybill.price} تومان")
                mainViewModel.setHeaderVisibility(true)
                findNavController().popBackStack()
            },{
                Timber.d(it)
            })
    }

}