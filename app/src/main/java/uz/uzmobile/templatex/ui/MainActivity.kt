package uz.uzmobile.templatex.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.MainActivityBinding
import uz.uzmobile.templatex.viewModel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.extension.inputMethodManager


class MainActivity : AppCompatActivity() {

    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }

    val viewModel: MainViewModel by viewModel()

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()

        navController.addOnDestinationChangedListener(viewModel.listener)

        viewModel.navDestination.observe(this, Observer {
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
        })
    }

    fun initViews() {
        binding.apply {
            viewModel = this@MainActivity.viewModel
            executePendingBindings()

            setSupportActionBar(toolbar)

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.selectionFragment,
                    R.id.catalogFragment,
                    R.id.favoriteFragment,
                    R.id.profileFragment,
                    R.id.cartFragment
                )
            )

            toolbar.setupWithNavController(navController, appBarConfiguration)

            bottomNavigationView.setOnCheckedChangeListener { group, id ->
                navController.navigate(id)
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}
