package uz.uzmobile.templatex.ui.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.cart_fragment.*

import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.CheckoutFragmentBinding

class CheckoutFragment : Fragment() {

    val viewModel: CheckoutViewModel by viewModel()

    private val binding by lazy { CheckoutFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CheckoutFragment()
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
        }
    }
}