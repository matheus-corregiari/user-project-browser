package br.com.matheus.userprojectbrowser.sdk

import okhttp3.Credentials

internal object SessionRepository {

    private var username: String = BuildConfig.USERNAME
        set(value) {
            if (field != value) authorization = null
            field = value
        }

    private var password: String = BuildConfig.PASSWORD
        set(value) {
            if (field != value) authorization = null
            field = value
        }

    var authorization: String? = null
        private set
        get() {
            field = field ?: Credentials.basic(username, password)
            return field
        }

}