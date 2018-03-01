package br.com.matheus.userprojectbrowser

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import br.com.matheus.userprojectbrowser.base.BaseActivity
import br.com.matheus.userprojectbrowser.base.BaseFragment
import br.com.matheus.userprojectbrowser.base.extension.fragmentTransaction

class TestActivity : BaseActivity() {

    private lateinit var content: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        content = FrameLayout(this)
        content.setBackgroundColor(Color.WHITE)
        content.id = android.R.id.content
        setContentView(content)
    }

    fun setFragment(fragment: BaseFragment) {
        fragmentTransaction { add(android.R.id.content, fragment, "TAG") }
    }

    fun setView(view: View) = runOnUiThread {
        content.addView(view)
    }
}
