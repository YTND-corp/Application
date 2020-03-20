package uz.uzmobile.templatex.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.databinding.FavoriteFragmentBinding

class FavoriteFragment : Fragment() {

    val viewModel: FavoriteViewModel by viewModel()

    private val binding by lazy { FavoriteFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: ProductAdapter

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@FavoriteFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel.products.observe(requireActivity(), Observer {
            if (it.isNullOrEmpty()) {
                binding.placeholderView.visibility = View.VISIBLE
            } else {
                binding.placeholderView.visibility = View.GONE
                adapter.setItems(it)
                adapter.notifyDataSetChanged()
            }

        })
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@FavoriteFragment.viewModel
            executePendingBindings()

            adapter = ProductAdapter(arrayListOf())
            favorites.hasFixedSize()
            favorites.adapter = adapter
        }
    }
}
