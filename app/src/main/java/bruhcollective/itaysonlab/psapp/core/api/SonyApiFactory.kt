package bruhcollective.itaysonlab.psapp.core.api

import bruhcollective.itaysonlab.psapp.core.ApiConstants
import bruhcollective.itaysonlab.psapp.core.api.impl.DmsApi
import bruhcollective.itaysonlab.psapp.core.api.impl.MainPsnApi
import bruhcollective.itaysonlab.psapp.core.api.impl.SsoLoginApi
import bruhcollective.itaysonlab.psapp.database.DatabaseHolder
import bruhcollective.itaysonlab.psapp.prefs.BSPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object SonyApiFactory {
    private var cachedToken = ""

    private val client by lazy {
        OkHttpClient()
    }

    private val clientWithAuth by lazy {
        OkHttpClient().newBuilder().apply {
            addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().apply {
                    if (cachedToken == "") {
                        runBlocking {
                            cachedToken = withContext(Dispatchers.IO) { DatabaseHolder.database.auth().get()!!.accountToken }
                        }
                    }

                    header("Authorization", "Bearer $cachedToken")
                }.build())
            }
        }.build()
    }

    private fun createRetrofit(base: String) = Retrofit.Builder().apply {
        client(clientWithAuth)
        baseUrl(base)
        addConverterFactory(MoshiConverterFactory.create())
    }.build()

    //

    val sso by lazy {
        Retrofit.Builder().apply {
            client(client)
            baseUrl(ApiConstants.CA_AUTH_URL)
            addConverterFactory(MoshiConverterFactory.create())
        }.build().create<SsoLoginApi>()
    }

    val dms by lazy {
        createRetrofit(ApiConstants.DMS_ROOT).create<DmsApi>()
    }

    val main by lazy {
        Retrofit.Builder().apply {
            client(clientWithAuth.newBuilder().addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().apply {
                    header("x-psn-app-ver", "PlayStationApp-Android/23.5.1")
                }.build())
            }.build())

            baseUrl(ApiConstants.MAIN_API)
            addConverterFactory(MoshiConverterFactory.create())
        }.build().create<MainPsnApi>()
    }
}