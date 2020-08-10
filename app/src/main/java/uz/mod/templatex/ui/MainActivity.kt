package uz.mod.templatex.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.forEach
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.badge.BadgeDrawable
import kotlinx.android.synthetic.main.main_activity.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import org.jetbrains.annotations.NotNull
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.analytics.AnalyticsManager
import uz.mod.templatex.analytics.EventBuilder
import uz.mod.templatex.databinding.MainActivityBinding
import uz.mod.templatex.ui.parent.ParentActivity
import uz.mod.templatex.utils.extension.color
import uz.mod.templatex.utils.extension.drawable
import uz.mod.templatex.utils.extension.setupWithNavController


class MainActivity : ParentActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var currentNavController: LiveData<NavController>

    private val binding by lazy { MainActivityBinding.inflate(layoutInflater) }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var cartBadge: BadgeDrawable? = null

    private val topLevelFragments = setOf(
        R.id.selectionFragment,
        R.id.categoryFragment,
        R.id.favoriteFragment,
        R.id.profileFragment,
        R.id.cartFragment
    )

    private val destinationGraphResourceIds = listOf(
        R.navigation.home_graph,
        R.navigation.catalog_graph,
        R.navigation.favorites_graph,
        R.navigation.profile_graph,
        R.navigation.cart_graph
    )

    private val bottomNavMenuItemIds = listOf(
        R.id.home_graph,
        R.id.catalog_graph,
        R.id.starred_graph,
        R.id.profile_graph,
        R.id.cart_graph
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        val analyticsManager = AnalyticsManager(this)
        val event = EventBuilder().withEventName("TEST").withEventField("field", "value").build(this)
        analyticsManager.sendEvent(event)

        binding.lifecycleOwner = this
        setContentView(binding.root)

        initViews()

        KeyboardVisibilityEvent.setEventListener(this) {
            Timber.e("Keyboard = $it")
            mainViewModel.keyboardVisibilityChanged(it)
        }

        mainViewModel.hasBackButton.observe(this, Observer {
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(it)
                setHomeAsUpIndicator(
                    when {
                        mainViewModel.destination.value?.id == R.id.searchFragment -> R.drawable.ic_close
                        it -> R.drawable.ic_back
                        else -> 0
                    }
                )
            }
        })



        mainViewModel.title.observe(this, Observer {
            binding.toolbar.title = ""
        })
        mainViewModel.toolbarGrayBackground.observe(this, Observer { titleGrayBackground ->
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

        mainViewModel.getCartItemCount().observe(this, Observer { count ->
            if (count == 0) {
                cartBadge?.isVisible = false
            } else {
                cartBadge?.isVisible = true
                cartBadge?.number = count
            }
        })
    }

    fun initViews() {
        binding.apply {
            viewModel = mainViewModel
            executePendingBindings()
            setSupportActionBar(toolbar)

            val menuItem = bottom_navigation_view.menu.findItem(R.id.cart_graph)
            cartBadge = bottom_navigation_view.getOrCreateBadge(menuItem.itemId)
            cartBadge?.badgeTextColor = ContextCompat.getColor(root.context, R.color.white)
            cartBadge?.backgroundColor = ContextCompat.getColor(root.context, R.color.black)
            cartBadge?.isVisible = false

            appBarConfiguration = AppBarConfiguration(topLevelFragments)

            val controller = bottomNavigationView.setupWithNavController(
                destinationGraphResourceIds, supportFragmentManager, R.id.nav_host_container, intent, bottomNavMenuItemIds
            )
            currentNavController = controller
            controller.observe(this@MainActivity, Observer { navController ->
                onNavControllerChanged(navController)
            })
            bottomNavigationView.selectedItemId = R.id.home_graph
            binding.toolbar.title = ""

            mainViewModel.bottomNavBarSelection.observe(this@MainActivity, Observer {
                it.getContentIfNotHandled()?.let { customArgs ->
                    /*bottomNavMenuItemIds.indexOf(customArgs.destGraphID).let { menuIndex ->
                        if (menuIndex != -1) bottomNavigationView.menu.getItem(menuIndex).isChecked = true
                    }
                    currentNavController.value?.navigate(customArgs.destFragmentID)*/
                    bottomNavigationView.selectedItemId = customArgs.destGraphID
                }
            })



            bottomNavigationView.menu.forEach {
                findViewById<View>(it.itemId).setOnLongClickListener {
                    true
                }
            }
        }
    }

    override fun onBackPressed() {
      /*  currentNavController.value?.graph?.id?.let {
            bottomNavMenuItemIds.indexOf(it).let { menuIndex ->
                if (menuIndex != -1) binding.bottomNavigationView.menu.getItem(menuIndex).isChecked = true
            }
        }*/
        super.onBackPressed()
    }

    private fun @NotNull MainActivityBinding.onNavControllerChanged(
        navController: NavController
    ) {
        hideKeyboard()
        hideLoading()
        toolbar.setupWithNavController(navController, appBarConfiguration)
        viewModel!!.destinationChanged(navController.currentDestination!!)
    }

    override fun onSupportNavigateUp(): Boolean =
        NavigationUI.navigateUp(currentNavController.value!!, appBarConfiguration) || super.onSupportNavigateUp()
}
