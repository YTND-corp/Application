package uz.mod.templatex.ui.code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.mod.templatex.databinding.CodeFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        viewModel.responce.observe(this@CodeFragment, Observer { result ->
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
                    findNavController().navigate(CodeFragmentDirections.actionCodeFragmentToDeliveryFragment(result.data, args.request))
                }
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner =  this@CodeFragment
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
                if (it.length==4) {
                    viewModel?.confirm()
                }
            })

            resendButton.setOnClickListener {

            }

        }
    }
}
