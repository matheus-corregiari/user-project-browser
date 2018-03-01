package br.com.matheus.userprojectbrowser.sdk.data.remote.interceptor

import br.com.matheus.userprojectbrowser.sdk.SessionRepository
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class AuthInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response = with(chain.request().newBuilder()) {
        removeHeader("Authorization")
        SessionRepository.authorization
                .takeIf { !it.isNullOrEmpty() }
                ?.let { addHeader("Authorization", it) }
        chain.proceed(build())
    }

}