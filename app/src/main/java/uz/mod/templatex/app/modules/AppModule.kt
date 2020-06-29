package uz.mod.templatex.app.modules

import android.app.Application
import android.text.Spannable
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import uz.aqlify.yonda.utils.Prefs
import uz.mod.templatex.BuildConfig
import uz.mod.templatex.model.local.db.AppDatabase
import uz.mod.templatex.model.remote.api.*
import uz.mod.templatex.model.remote.network.AppExecutors
import uz.mod.templatex.model.remote.network.AuthInterceptor
import uz.mod.templatex.model.remote.network.LiveDataCallAdapterFactory
import uz.mod.templatex.model.remote.network.NetworkInterceptor
import uz.mod.templatex.model.repository.*
import uz.mod.templatex.ui.MainViewModel
import uz.mod.templatex.ui.about.AboutViewModel
import uz.mod.templatex.ui.adres.AdresViewModel
import uz.mod.templatex.ui.askQuestion.AskQuestionViewModel
import uz.mod.templatex.ui.callMe.CallMeViewModel
import uz.mod.templatex.ui.cart.CartViewModel
import uz.mod.templatex.ui.category.CategoryChildViewModel
import uz.mod.templatex.ui.category.CategoryViewModel
import uz.mod.templatex.ui.checkOrderStatus.CheckOrderStatusViewModel
import uz.mod.templatex.ui.checkout.CheckoutViewModel
import uz.mod.templatex.ui.country.CountryViewModel
import uz.mod.templatex.ui.favorite.FavoriteViewModel
import uz.mod.templatex.ui.filter.FilterViewModel
import uz.mod.templatex.ui.product.ProductViewModel
import uz.mod.templatex.ui.products.ProductsViewModel
import uz.mod.templatex.ui.profile.authorized.ProfileAuthorizedViewModel
import uz.mod.templatex.ui.profile.authorized.myOrder.ProfileMyOrderViewModel
import uz.mod.templatex.ui.profile.authorized.myOrders.ProfileMyOrdersViewModel
import uz.mod.templatex.ui.profile.guest.ProfileGuestViewModel
import uz.mod.templatex.ui.recoveryPassword.RecoveryPasswordViewModel
import uz.mod.templatex.ui.search.SearchViewModel
import uz.mod.templatex.ui.selection.SelectionChildViewModel
import uz.mod.templatex.ui.selection.SelectionViewModel
import uz.mod.templatex.ui.signIn.SignInViewModel
import uz.mod.templatex.ui.signUp.SignUpViewModel
import uz.mod.templatex.ui.splash.SplashViewModel
import uz.mod.templatex.ui.subCategory.SubCategoryViewModel
import uz.mod.templatex.ui.supportCenter.SupportCenterViewModel
import uz.mod.templatex.utils.Const
import java.io.File
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }

    viewModel { FavoriteViewModel(get(), get()) }

    viewModel { SignUpViewModel(get(), get()) }
    viewModel { SignInViewModel(get(), get()) }
    viewModel { RecoveryPasswordViewModel(get()) }

    viewModel { SelectionViewModel(get(), get()) }
    viewModel { SelectionChildViewModel(get()) }
    viewModel { SearchViewModel(get()) }

    viewModel { CategoryViewModel(get(), get()) }
    viewModel { CategoryChildViewModel(get()) }
    viewModel { SubCategoryViewModel(get()) }

    viewModel {
        ProfileGuestViewModel(
            get(),
            get()
        )
    }
    viewModel { ProfileAuthorizedViewModel(get()) }
    viewModel { ProfileMyOrdersViewModel(get()) }
    viewModel { ProfileMyOrderViewModel(get()) }
    viewModel { CountryViewModel(get()) }
    viewModel { CallMeViewModel(get()) }
    viewModel { CheckOrderStatusViewModel(get()) }
    viewModel { AskQuestionViewModel(get()) }
    viewModel { SupportCenterViewModel(get()) }
    viewModel { AboutViewModel(get()) }

    viewModel { CartViewModel(get(), get()) }
    viewModel { CheckoutViewModel(get(), get()) }
    viewModel { AdresViewModel(get()) }

    viewModel { ProductsViewModel(get(), get()) }
    viewModel { ProductViewModel(get(), get()) }
    viewModel { FilterViewModel(get()) }
}

val prefsModule = module {
    single { Prefs(get()) }
    factory { AppExecutors()}
}

val dbModule = module {
    factory {
        Room.databaseBuilder(get(), AppDatabase::class.java, Const.DB_NAME).allowMainThreadQueries()
            .build()
    }
    factory { get<AppDatabase>().productDao() }
    factory { get<AppDatabase>().filterDao() }
}

val repositoryModule = module {
    single { CategoryRepository(get()) }
    single { ProductRepository(get(), get(), get(), get()) }
    single { AuthRepository(get(), get()) }
    single { CartRepository(get()) }
    single { CheckoutRepository(get(), get()) }
}

val apiModule = module {
    factory { get<Retrofit>().create(CategoryService::class.java) }
    factory { get<Retrofit>().create(ProductService::class.java) }
    factory { get<Retrofit>().create(AuthService::class.java) }
    factory { get<Retrofit>().create(CartService::class.java) }
    factory { get<Retrofit>().create(CheckoutService::class.java) }
}

val retrofitModule = module {

    fun provideFile(application: Application): File {
        val file = File(application.cacheDir, "OkHttpCache")
        file.mkdirs()
        return file
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Timber.tag("OkHttp").d(message)
            })
        logging.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    fun provideX509TrustManager(): X509TrustManager {
        return object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }

            @Throws(CertificateException::class)
            override fun checkClientTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(
                chain: Array<java.security.cert.X509Certificate>,
                authType: String
            ) {
            }
        }
    }

    fun providesSSLSocketFactory(trustManager: X509TrustManager): SSLSocketFactory {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf(trustManager)

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            return sslContext.socketFactory
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun provideHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkInterceptor,
        authInterceptor: AuthInterceptor,
        trustManager: X509TrustManager,
        sslSocketFactory: SSLSocketFactory
    ) = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .cache(cache)
        .addInterceptor(httpLoggingInterceptor)
        .addNetworkInterceptor(networkInterceptor)
        .addInterceptor(authInterceptor)
        .sslSocketFactory(sslSocketFactory, trustManager)
        .build()


    fun provideRetrofit(factory: Gson, client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(Const.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .client(client)
        .build()


    single { provideFile(get()) }
    single { Cache(get(), 10 * 1000 * 1000) }
    single { GsonBuilder().create() }
    single { NetworkInterceptor(get()) }
    single { provideHttpLoggingInterceptor() }
    single { AuthInterceptor(get()) }
    single { provideX509TrustManager() }
    single { providesSSLSocketFactory(get()) }
    single { provideHttpClient(get(), get(), get(), get(), get(), get()) }
    single { provideRetrofit(get(), get()) }
}

val appModules =
    listOf(prefsModule, retrofitModule, apiModule, dbModule, repositoryModule, viewModelModule)
