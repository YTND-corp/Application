package uz.mod.templatex.ui.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.MainActivityBinding
import uz.mod.templatex.extension.inputMethodManager
import uz.mod.templatex.ui.parent.ParentActivity

class MainActivity : ParentActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }
    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val TOP_LEVEL_FRAGMENTS = setOf(
        R.id.selectionFragment,
        R.id.categoryFragment,
        R.id.favoriteFragment,
        R.id.profileFragment,
        R.id.cartFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()

        navController.addOnDestinationChangedListener { _, destination, _ ->
//            viewModel.destinationChanged(destination)
            hideKeyboard()
        }

        KeyboardVisibilityEvent.setEventListener(this) {
            Timber.e("Keyboard = $it")
            viewModel.keyboardVisibilityChanged(it)
        }

        viewModel.hasBackButton.observe(this, Observer {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(it)
                setHomeAsUpIndicator(
                    when {
                        viewModel.destination.value?.id == R.id.searchFragment -> R.drawable.ic_close
                        it -> R.drawable.ic_back
                        else -> 0
                    }
                )
            }
        })

        viewModel.destinationChanged(navController.currentDestination!!)

        viewModel.title.observe(this, Observer {
            binding.toolbar.title = ""
        })
    }

    fun initViews() {
        binding.apply {
            viewModel = this@MainActivity.viewModel
            executePendingBindings()
            setSupportActionBar(toolbar)

            appBarConfiguration = AppBarConfiguration(TOP_LEVEL_FRAGMENTS)

            toolbar.setupWithNavController(navController, appBarConfiguration)
            bottomNavigationView.setupWithNavController(navController)
            binding.toolbar.title = ""
        }
    }

    private fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp()
}
