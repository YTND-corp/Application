package uz.uzmobile.templatex.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.main_activity.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.MainActivityBinding
import uz.uzmobile.templatex.extension.inputMethodManager
import uz.uzmobile.templatex.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {

    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }

    val viewModel: MainViewModel by viewModel()

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }

    val TOP_LEVEL_FRAGMENTS = setOf(
        R.id.selectionFragment,
        R.id.catalogFragment,
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
            viewModel.destinationChanged(destination)
            hidekeyboard()
        }

        KeyboardVisibilityEvent.setEventListener(this) {
            viewModel.keyboardVisibilityChanged(it)
        }

        viewModel.hasBackButton.observe(this, Observer {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(it)
                setHomeAsUpIndicator(if (it) R.drawable.ic_back else 0)
            }
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
        }
    }

    private fun hidekeyboard() {
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0);
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }
}
