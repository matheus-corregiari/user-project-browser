package br.com.matheus.userprojectbrowser

import android.app.Application
import br.com.matheus.userprojectbrowser.sdk.setupApplication

class ProjectApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupApplication(this)
    }
}
