package br.com.matheus.userprojectbrowser.sdk.model

import br.com.matheus.userprojectbrowser.sdk.model.type.DataResultStatus

data class DataResult<out T>(
        val data: T?,
        var error: Throwable?,
        @get:DataResultStatus
        @DataResultStatus val resultStatus: Long,
        val STATUS: String?
)