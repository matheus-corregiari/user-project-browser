package br.com.matheus.userprojectbrowser.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import br.com.matheus.userprojectbrowser.R
import br.com.matheus.userprojectbrowser.base.BaseActivity
import br.com.matheus.userprojectbrowser.base.delegate.extraProvider
import br.com.matheus.userprojectbrowser.base.delegate.viewProvider
import br.com.matheus.userprojectbrowser.base.extension.enableBack
import br.com.matheus.userprojectbrowser.base.extension.fullDate
import br.com.matheus.userprojectbrowser.base.extension.loadUrl
import br.com.matheus.userprojectbrowser.sdk.model.domain.ProjectVO

class ProjectActivity : BaseActivity() {

    private val project: ProjectVO? by extraProvider(EXTRA_PROJECT)

    // Views
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val icon: ImageView by viewProvider(R.id.project_icon)
    private val name: TextView by viewProvider(R.id.project_name)
    private val createDate: TextView by viewProvider(R.id.create_date)
    private val lastUpdateDate: TextView by viewProvider(R.id.last_update_date)
    private val description: TextView by viewProvider(R.id.project_description)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        setSupportActionBar(toolbar)
        supportActionBar.enableBack()

        project?.let {
            icon.loadUrl(it.logo, R.drawable.ic_placeholder)
            name.text = it.name
            description.text = it.description
            createDate.text = it.createdOn.fullDate()
            lastUpdateDate.text = it.lastChangedOn.fullDate()
        }
    }

    companion object {
        private const val EXTRA_PROJECT = "EXTRA_PROJECT"
        fun intent(context: Context, project: ProjectVO): Intent {
            return Intent(context, ProjectActivity::class.java).putExtra(EXTRA_PROJECT, project)
        }
    }

}
