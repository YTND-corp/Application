package uz.uzmobile.templatex.ui.about


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.AboutFragmentBinding

class AboutFragment : Fragment() {

    val viewModel: AboutViewModel by viewModel()


    private val binding by lazy { AboutFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.about_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutFragment -> true;
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@AboutFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@AboutFragment.viewModel
            executePendingBindings()

        }
    }
}