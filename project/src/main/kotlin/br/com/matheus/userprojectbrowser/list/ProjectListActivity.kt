package br.com.matheus.userprojectbrowser.list

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import br.com.matheus.userprojectbrowser.R
import br.com.matheus.userprojectbrowser.base.BaseActivity
import br.com.matheus.userprojectbrowser.base.adapter.SimpleAdapter
import br.com.matheus.userprojectbrowser.base.delegate.viewModelProvider
import br.com.matheus.userprojectbrowser.base.delegate.viewProvider
import br.com.matheus.userprojectbrowser.base.statemachine.ViewStateMachine
import br.com.matheus.userprojectbrowser.base.view.WarningView
import br.com.matheus.userprojectbrowser.detail.ProjectActivity
import br.com.matheus.userprojectbrowser.sdk.model.domain.ProjectVO
import br.com.matheus.userprojectbrowser.view.ItemProjectView

private const val SUCCESS_STATE = 0
private const val LOADING_STATE = 1
private const val EMPTY_STATE = 2
private const val ERROR_STATE = 3
private const val STATE_MACHINE = "STATE_MACHINE"

class ProjectListActivity : BaseActivity() {

    private val viewModel by viewModelProvider(ProjectListViewModel::class)

    private val stateMachine = ViewStateMachine()
    private val adapter = SimpleAdapter(::ItemProjectView).withListener(this::onItemCLick)

    // Views
    private val toolbar: Toolbar by viewProvider(R.id.toolbar)
    private val emptyView: WarningView by viewProvider(R.id.empty_view)
    private val loadingView: View by viewProvider(R.id.progress_view)
    private val errorView: WarningView by viewProvider(R.id.error_view)
    private val recyclerView: RecyclerView by viewProvider(R.id.recycler_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_list)
        setSupportActionBar(toolbar)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        errorView.setOnClickListener { viewModel.projectListLiveData.invalidate() }

        setupStateMachine(savedInstanceState)
        startObserveData()
    }

    private fun setupStateMachine(bundle: Bundle?) = stateMachine.setup(initialState = LOADING_STATE, restoreState = bundle) {
        add(SUCCESS_STATE) {
            visibles(recyclerView)
            gones(loadingView, emptyView, errorView)
        }
        add(LOADING_STATE) {
            visibles(loadingView)
            gones(recyclerView, emptyView, errorView)
        }
        add(EMPTY_STATE) {
            visibles(emptyView)
            gones(loadingView, recyclerView, errorView)
        }
        add(ERROR_STATE) {
            visibles(errorView)
            gones(loadingView, emptyView, recyclerView)
        }
    }

    private fun startObserveData() = with(viewModel.projectListLiveData) {
        observeLoading(this@ProjectListActivity) {
            if (it) stateMachine.changeState(LOADING_STATE)
        }

        observeError(this@ProjectListActivity) {
            stateMachine.changeState(ERROR_STATE)
        }

        observeData(this@ProjectListActivity) {
            stateMachine.changeState(if (it.isNotEmpty()) SUCCESS_STATE else EMPTY_STATE)
            adapter.setList(it)
        }
    }

    private fun onItemCLick(project: ProjectVO) {
        startActivity(ProjectActivity.intent(this, project))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(STATE_MACHINE, stateMachine.saveInstanceState())
    }

}