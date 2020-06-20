package uz.mod.templatex.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.databinding.FavoriteFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.products.ProductAdapter

class FavoriteFragment : ParentFragment() {

    val viewModel: FavoriteViewModel by viewModel()

    private val binding by lazy { FavoriteFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: ProductAdapter

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = ProductAdapter { id, isFavorite ->
            viewModel.favoriteToggle(id, isFavorite).observe(this, Observer { result ->
                when (result.status) {
                    Status.LOADING -> showLoading()
                    Status.ERROR -> {
                        hideLoading()
                        showError(result.error)
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                    }
                }
            })
        }

        viewModel.products().observe(this, Observer {
            if (it.isNullOrEmpty()) {
                binding.placeholderView.visibility = View.VISIBLE
                adapter.setItems(it)
            } else {
                binding.placeholderView.visibility = View.GONE
                adapter.setItems(it)
            }
        })

        viewModel.getFavorites().observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                }
            }
        })
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
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@FavoriteFragment.viewModel
            executePendingBindings()

            products.adapter = adapter
        }
    }
}
