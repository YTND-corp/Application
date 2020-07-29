package uz.mod.templatex.ui.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.FavoriteFragmentBinding
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.products.ProductAdapter
import uz.mod.templatex.utils.extension.lazyFast

class FavoriteFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    val viewModel: FavoriteViewModel by viewModel()

    private val binding by lazy { FavoriteFragmentBinding.inflate(layoutInflater) }

    private lateinit var adapter: FavoriteAdapter

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = FavoriteAdapter { id, isFavorite ->
            viewModel.favoriteToggle(id).observe(this, Observer { result ->
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

        viewModel.response.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    showError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    adapter.setItems(result.data)
                }
            }
        })

        viewModel.getFavorites()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@FavoriteFragment.viewModel
            executePendingBindings()

            products.adapter = adapter

            placeholderButton.setOnClickListener {
                navController.navigate(R.id.action_favoriteFragment_to_categoryFragment)
            }
        }
    }
}
