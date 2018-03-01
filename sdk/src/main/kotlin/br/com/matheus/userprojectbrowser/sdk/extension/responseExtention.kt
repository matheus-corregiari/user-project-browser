@file:JvmName("ResponseUtils")

package br.com.matheus.userprojectbrowser.sdk.extension

import br.com.matheus.userprojectbrowser.sdk.model.DataResult
import br.com.matheus.userprojectbrowser.sdk.model.type.DataResultStatus
import br.com.matheus.userprojectbrowser.sdk.model.type.ERROR
import br.com.matheus.userprojectbrowser.sdk.model.type.LOADING

internal fun loadingResponse() = DataResult(null, null, LOADING, "LOADING")

internal fun <T> T?.toDataResponse(@DataResultStatus status: Long, STATUS: String?) = DataResult(this, null, status, STATUS)

internal fun <T> T?.toDataResponseWithError(error: Throwable, STATUS: String) = DataResult(this, error, ERROR, STATUS)

internal fun <T> Throwable.toErrorResponse() = DataResult<T>(null, this, ERROR, "ERROR")