package br.com.matheus.userprojectbrowser.base

import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import br.com.matheus.userprojectbrowser.base.extension.toast

abstract class BaseActivity : AppCompatActivity() {

    open fun onErrorReceived(throwable: Throwable) {
        toast(throwable)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

}