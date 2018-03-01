package br.com.matheus.userprojectbrowser.sdk

import android.app.Application
import br.com.matheus.userprojectbrowser.sdk.prng.PrngFixes
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

fun setupApplication(application: Application) {
    AndroidThreeTen.init(application)
    PrngFixes.apply()
    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
}
