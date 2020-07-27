package uz.mod.templatex.ui.code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CodeFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.address.AddressFragmentDirections
import uz.mod.templatex.ui.parent.ParentFragment

class CodeFragment : ParentFragment() {

    val viewModel: CodeViewModel by viewModel()

    val args: CodeFragmentArgs by navArgs()

    private val binding by lazy { CodeFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CodeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setArguments(args)

        if (args.isCheckout) {
            viewModel.checkoutConfirmResponse.observe(this@CodeFragment, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        showError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        binding.code.text = null
                        sharedViewModel.loggedIn(result.data?.user)

                        if (args.isCheckout) {
                            findNavController().navigate(
                                AddressFragmentDirections.actionGlobalAddressFragment(
                                    result.data,
                                    args.phone
                                )
                            )
                        } else {
                            findNavController().popBackStack(R.id.profileFragment, true)
                        }
                    }
                }
            })
        } else {
            viewModel.authConfirmResponse.observe(this@CodeFragment, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        showError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        binding.code.text = null
                        sharedViewModel.loggedIn(result.data)
                        findNavController().navigate(R.id.action_codeFragment_to_profileFragment)
                    }
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CodeFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CodeFragment.viewModel
            executePendingBindings()

            viewModel?.code?.observe(viewLifecycleOwner, Observer {
                if (it.length == 4) {
                    if (args.isCheckout)
                        viewModel?.checkoutConfirm()
                    else
                        viewModel?.authConfirm()
                }
            })

            resendButton.setOnClickListener {

            }

            change.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
