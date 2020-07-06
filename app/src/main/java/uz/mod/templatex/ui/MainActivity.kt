package uz.mod.templatex.ui

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.jetbrains.annotations.NotNull
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.databinding.MainActivityBinding
import uz.mod.templatex.ui.parent.ParentActivity
import uz.mod.templatex.utils.extension.color
import uz.mod.templatex.utils.extension.drawable
import uz.mod.templatex.utils.extension.inputMethodManager
import uz.mod.templatex.utils.extension.setupWithNavControllerMultipleStack


class MainActivity : ParentActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var currentNavController : LiveData<NavController>

    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }

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



        viewModel.title.observe(this, Observer {
            binding.toolbar.title = ""
        })
        viewModel.toolbarGrayBackground.observe(this, Observer { titleGrayBackground ->
            val drawable =
                if (titleGrayBackground) drawable(R.color.windowBackgroundGrayColor)
                else drawable(R.color.windowBackgroundWhiteColor)
            val color =
                if (titleGrayBackground) color(R.color.windowBackgroundGrayColor)
                else color(R.color.windowBackgroundWhiteColor)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color
            }

            binding.toolbar.background = drawable
        })
    }

    fun initViews() {
        binding.apply {
            viewModel = this@MainActivity.viewModel
            executePendingBindings()
            setSupportActionBar(toolbar)

            appBarConfiguration = AppBarConfiguration(TOP_LEVEL_FRAGMENTS)

            val controller = bottomNavigationView.setupWithNavControllerMultipleStack(listOf(
                R.navigation.home_graph,
                R.navigation.catalog_graph,
                R.navigation.starred_graph,
                R.navigation.profile_graph,
                R.navigation.cart_graph
            ),supportFragmentManager,R.id.nav_host_container)
            currentNavController = controller
            controller.observe(this@MainActivity, Observer {navController->
                onNavControllerChanged(navController)
            })
            bottomNavigationView.selectedItemId = R.id.home_graph
//            onNavControllerChanged(controller.value!!)
//            bottomNavigationView.setupWithNavController(navController)
            binding.toolbar.title = ""
        }
    }

    private fun @NotNull MainActivityBinding.onNavControllerChanged(
        navController: NavController
    ) {
        hideKeyboard()
        toolbar.setupWithNavController(navController, appBarConfiguration)
        viewModel!!.destinationChanged(navController.currentDestination!!)
    }

    private fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(currentNavController.value!!, appBarConfiguration) || super.onSupportNavigateUp()
}
