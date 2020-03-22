package uz.uzmobile.templatex.app.modules

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import uz.uzmobile.templatex.BuildConfig
import uz.uzmobile.templatex.model.remote.api.ApiService
import uz.uzmobile.templatex.model.repository.ApiRepository
import java.io.File
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager
import org.koin.androidx.viewmodel.dsl.viewModel
import uz.aqlify.yonda.utils.Prefs
import uz.uzmobile.templatex.model.remote.network.AuthInterceptor
import uz.uzmobile.templatex.model.remote.network.LiveDataCallAdapterFactory
import uz.uzmobile.templatex.model.remote.network.NetworkInterceptor
import uz.uzmobile.templatex.ui.about.AboutViewModel
import uz.uzmobile.templatex.ui.adres.AdresViewModel
import uz.uzmobile.templatex.ui.askQuestion.AskQuestionViewModel
import uz.uzmobile.templatex.ui.callMe.CallMeViewModel
import uz.uzmobile.templatex.ui.cart.CartViewModel
import uz.uzmobile.templatex.ui.catalog.CatalogChildViewModel
import uz.uzmobile.templatex.ui.catalog.CatalogViewModel
import uz.uzmobile.templatex.ui.catalogDetails.CatalogDetailViewModel
import uz.uzmobile.templatex.ui.checkOrderStatus.CheckOrderStatusViewModel
import uz.uzmobile.templatex.ui.checkout.CheckoutViewModel
import uz.uzmobile.templatex.ui.country.CountryViewModel
import uz.uzmobile.templatex.ui.favorite.FavoriteViewModel
import uz.uzmobile.templatex.ui.product.ProductViewModel
import uz.uzmobile.templatex.ui.products.ProductsViewModel
import uz.uzmobile.templatex.ui.profile.ProfileViewModel
import uz.uzmobile.templatex.ui.recoveryPassword.RecoveryPasswordViewModel
import uz.uzmobile.templatex.ui.selection.SelectionChildViewModel
import uz.uzmobile.templatex.ui.selection.SelectionViewModel
import uz.uzmobile.templatex.ui.signIn.SignInViewModel
import uz.uzmobile.templatex.ui.signUp.SignUpViewModel
import uz.uzmobile.templatex.ui.support.SupportViewModel
import uz.uzmobile.templatex.utils.Const
import uz.uzmobile.templatex.viewModel.*


val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { BrandsViewModel(get(), get()) }

    viewModel { FavoriteViewModel(get()) }

    viewModel { SignUpViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { RecoveryPasswordViewModel(get()) }

    viewModel { SelectionViewModel(get()) }
    viewModel { SelectionChildViewModel(get()) }

    viewModel { CatalogViewModel(get()) }
    viewModel { CatalogChildViewModel(get()) }
    viewModel { CatalogDetailViewModel(get()) }

    viewModel { ProfileViewModel(get()) }
    viewModel { CountryViewModel(get()) }
    viewModel { CallMeViewModel(get()) }
    viewModel { CheckOrderStatusViewModel(get()) }
    viewModel { AskQuestionViewModel(get()) }
    viewModel { SupportViewModel(get()) }
    viewModel { AboutViewModel(get()) }

    viewModel { CartViewModel(get()) }
    viewModel { CheckoutViewModel(get()) }
    viewModel { AdresViewModel(get()) }

    viewModel { ProductsViewModel(get()) }
    viewModel { ProductViewModel(get()) }


}

val prefsModule = module {
    fun providePrefs(application: Application): Prefs {
        return Prefs(application)
    }

    single { providePrefs(get()) }
}

val repositoryModule = module {
    fun provideApiRepository(service: ApiService): ApiRepository {
        return ApiRepository(service)
    }

    single { provideApiRepository(get()) }
}

val apiModule = module {
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    single { provideApiService(get()) }
}

val retrofitModule = module {

    fun provideFile(application: Application): File {
        val file = File(application.cacheDir, "OkHttpCache")
        file.mkdirs()
        return file
    }

    fun provideCache(file: File): Cache {
        return Cache(file, 10 * 1000 * 1000) // 10 mb cache file
    }

    fun provideGson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
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

    fun provideNetworkInterceptor(application: Application): NetworkInterceptor {
        return NetworkInterceptor(application)
    }

    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
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
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .cache(cache)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(networkInterceptor)
            .addInterceptor(authInterceptor)
            .sslSocketFactory(sslSocketFactory, trustManager)
            .build()
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(client)
            .build()
    }

    single { provideFile(get()) }
    single { provideCache(get()) }
    single { provideGson() }
    single { provideNetworkInterceptor(get()) }
    single { provideHttpLoggingInterceptor() }
    single { provideAuthInterceptor() }
    single { provideX509TrustManager() }
    single { providesSSLSocketFactory(get()) }
    single { provideHttpClient(get(), get(), get(), get(), get(), get()) }
    single { provideRetrofit(get(), get()) }
}

val appModules = listOf(prefsModule, retrofitModule, apiModule, repositoryModule, viewModelModule)
