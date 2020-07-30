package uz.mod.templatex.ui.profile.authorized.myFavorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileMyFavoriteFragmentBinding
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.model.remote.network.Status
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Const
import uz.mod.templatex.utils.extension.lazyFast

class ProfileMyFavoriteFragment : ParentFragment() {


    private val navController by lazyFast { findNavController() }
    val viewModel: ProfileMyFavoriteViewModel by viewModel()

    private val binding by lazy { ProfileMyFavoriteFragmentBinding.inflate(layoutInflater) }
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoriteAdapter(object : FavoriteAdapter.FavoriteAdapterListener {
            override fun onRemoveClick(id: Int) {
                viewModel.toggleFavorite(id).observe(viewLifecycleOwner, Observer { result ->
                    when (result.status) {
                        Status.LOADING -> showLoading()
                        Status.ERROR -> {
                            hideLoading()
                            processError(result.error)
                        }
                        Status.SUCCESS -> {
                            hideLoading()
                        }
                    }
                })
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

        viewModel.getFavorites().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.LOADING -> showLoading()
                Status.ERROR -> {
                    hideLoading()
                    processError(result.error)
                }
                Status.SUCCESS -> {
                    hideLoading()
                    adapter.setItems(result.data)
                }
            }
        })
    }

    private fun initViews(): Unit = with(binding) {
        viewModel = this@ProfileMyFavoriteFragment.viewModel
        rvFavorites.adapter = adapter
    }

    private fun processError(error: ApiError?) {
        when (error?.code) {
            Const.API_NO_CONNECTION_STATUS_CODE -> navController.navigate(R.id.noInternetFragment)
            Const.API_SERVER_FAIL_STATUS_CODE -> navController.navigate(R.id.serverErrorDialogFragment)
            else -> showError(error)
        }
    }
}