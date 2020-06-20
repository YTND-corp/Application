package uz.mod.templatex.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.CheckoutFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.MaskWatcher

class CheckoutFragment : ParentFragment() {

    val viewModel: CheckoutViewModel by viewModel()

    private val binding by lazy { CheckoutFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CheckoutFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.responce.observe(this, Observer {result ->
            when(result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    if (result.data == true) {
                        viewModel.navigateToAddress.call()
                    }
                }
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CheckoutFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()


    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CheckoutFragment.viewModel
            executePendingBindings()

            phone.addTextChangedListener(MaskWatcher.phoneWatcher())

            phone.setOnFocusChangeListener { view, hasFocus ->

                if (hasFocus) {
                    if (phone.text?.length ?: 0 < 5 && phone.text?.equals("+998") == false) {
                        phone.setText("+998")
                    }
                } else {
                    if (phone.text?.length ?: 0 <= 5) {
                        phone.setText("")
                    }
                }
            }
        }
    }
}