package br.com.matheus.userprojectbrowser.sdk.data.remote

import br.com.matheus.userprojectbrowser.sdk.BuildConfig
import br.com.matheus.userprojectbrowser.sdk.data.remote.adapter.DateTypeAdapter
import br.com.matheus.userprojectbrowser.sdk.data.remote.factory.LiveDataCallAdapterFactory
import br.com.matheus.userprojectbrowser.sdk.data.remote.interceptor.AuthInterceptor
import br.com.matheus.userprojectbrowser.sdk.data.remote.interceptor.ResponseInterceptor
import br.com.matheus.userprojectbrowser.sdk.liveData.ResponseLiveData
import br.com.matheus.userprojectbrowser.sdk.model.DateType
import br.com.matheus.userprojectbrowser.sdk.model.domain.ProjectVO
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

internal interface Api {

    //region Project

    @GET("projects.json")
    fun listProjects(): ResponseLiveData<List<ProjectVO>>

    //endregion

}

internal val gson = GsonBuilder()
        .registerTypeAdapter(DateType::class.java, DateTypeAdapter())
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
        .create()

internal val apiInstance: Api = Retrofit.Builder()
        .addConverterFactory(buildGson())
        .client(buildClient())
        .addCallAdapterFactory(LiveDataCallAdapterFactory())
        .baseUrl(BuildConfig.BASE_URL).build().create(Api::class.java)

private fun buildClient(): OkHttpClient {
    val auth = AuthInterceptor()
    val logging = HttpLoggingInterceptor { Timber.i(it) }.setLevel(Level.BODY)
    val response = ResponseInterceptor()

    return OkHttpClient.Builder()
            .addInterceptor(auth)
            .addInterceptor(logging)
            .addInterceptor(response)
            .build()
}

private fun buildGson() = GsonConverterFactory.create(gson)