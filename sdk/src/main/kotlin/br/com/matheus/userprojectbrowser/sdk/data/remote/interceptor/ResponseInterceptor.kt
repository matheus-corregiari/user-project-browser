package br.com.matheus.userprojectbrowser.sdk.data.remote.interceptor

import br.com.matheus.userprojectbrowser.sdk.exception.*
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException

internal class ResponseInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val code = response.code()

        if (response.isSuccessful) return response
        val path = request.url().encodedPath()

        throw when (code) {
            504 -> GatewayTimeoutException("Gateway Timeout Exception", code, path)
            500 -> ServerException("Server Exception", code, path)
            429 -> TooManyRequestsException("Too Many Requests Exception", code, path)
            404 -> NotFoundException("Not Found Exception", code, path)
            403 -> ForbiddenException("Forbidden Exception", code, path)
            401 -> UnauthorizedException("Unauthorized Exception", code, path)
            400 -> BadRequestException("Bad Request Exception", code, path)
            else -> SdkException("Unmapped Http Exception", code, path)
        }
    }
}