package br.com.matheus.userprojectbrowser.sdk.exception

import br.com.matheus.userprojectbrowser.sdk.model.SdkError
import java.lang.Exception

open class SdkException internal constructor(message: String,
                                             val code: Int,
                                             val requestedPath: String,
                                             val error: SdkError = SdkError(message, code.toLong())) : Exception(message) {
    override fun toString(): String {
        return "code: $code\npath: $requestedPath\nerror: $error"
    }
}

class UnauthorizedException internal constructor(message: String, code: Int, requestedPath: String) :
        SdkException(message, code, requestedPath)

class ServerException internal constructor(message: String, code: Int, requestedPath: String) :
        SdkException(message, code, requestedPath)

class GatewayTimeoutException internal constructor(message: String, code: Int, requestedPath: String) :
        SdkException(message, code, requestedPath)

class NotFoundException internal constructor(message: String, code: Int, requestedPath: String) :
        SdkException(message, code, requestedPath)

class ForbiddenException internal constructor(message: String, code: Int, requestedPath: String) :
        SdkException(message, code, requestedPath)

class BadRequestException internal constructor(message: String, code: Int, requestedPath: String) :
        SdkException(message, code, requestedPath)

class TooManyRequestsException internal constructor(message: String, code: Int, requestedPath: String) :
        SdkException(message, code, requestedPath)