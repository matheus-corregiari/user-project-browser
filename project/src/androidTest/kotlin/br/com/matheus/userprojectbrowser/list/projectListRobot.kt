package br.com.matheus.userprojectbrowser.list

import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.actions.ClickActions.click
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.notDisplayed
import br.com.concretesolutions.kappuccino.custom.intent.IntentMatcherInteractions.matchIntent
import br.com.concretesolutions.kappuccino.custom.recyclerView.RecyclerViewInteractions.recyclerView
import br.com.matheus.userprojectbrowser.R
import br.com.matheus.userprojectbrowser.base.BaseActivityRobot
import br.com.matheus.userprojectbrowser.base.extension.mockCreation
import br.com.matheus.userprojectbrowser.base.extension.mockError
import br.com.matheus.userprojectbrowser.base.extension.mockLoading
import br.com.matheus.userprojectbrowser.base.extension.mockValue
import br.com.matheus.userprojectbrowser.base.factory.model.projectList
import br.com.matheus.userprojectbrowser.detail.ProjectActivity
import br.com.matheus.userprojectbrowser.sdk.ProjectRepository
import br.com.matheus.userprojectbrowser.sdk.liveData.ResponseLiveData
import br.com.matheus.userprojectbrowser.sdk.model.domain.ProjectVO

fun ProjectListTest.projectList(func: ProjectListRobot.() -> Unit): ProjectListRobot {
    ProjectRepository.listAllProjects().mockCreation(emptyList())
    return ProjectListRobot(rule, ProjectRepository.listAllProjects()).apply(func)
}

class ProjectListRobot(rule: ActivityTestRule<ProjectListActivity>,
                       private val liveData: ResponseLiveData<List<ProjectVO>>)
    : BaseActivityRobot<ProjectListActivity>(rule) {

    fun onErrorState() {
        launchActivity()
        receiveError {
            errorStateDisplayed()
        }
    }

    fun onSuccessState() {
        launchActivity()
        receiveList {
            successStateDisplayed()
        }
    }

    infix fun receiveLoading(func: ProjectListResult.() -> Unit) {
        liveData.mockLoading()
        ProjectListResult().apply(func)
    }

    infix fun receiveError(func: ProjectListResult.() -> Unit) {
        liveData.mockError(IllegalStateException("Mocked Error"))
        ProjectListResult().apply(func)
    }

    infix fun receiveEmpty(func: ProjectListResult.() -> Unit) {
        liveData.mockValue(emptyList())
        ProjectListResult().apply(func)
    }

    infix fun receiveList(func: ProjectListResult.() -> Unit) {
        liveData.mockValue(projectList())
        ProjectListResult().apply(func)
    }

    infix fun clickOnRetry(func: ProjectListResult.() -> Unit) {
        click {
            parent(R.id.error_view) { id(R.id.action_view) }
        }
        liveData.mockValue(emptyList())
        ProjectListResult().apply(func)
    }

    infix fun clickOnItem(func: ProjectListResult.() -> Unit) {

        matchIntent {
            className(ProjectActivity::class.java.name)
            result { ok() }
        }

        recyclerView(R.id.recycler_view) {
            atPosition(0) { click() }
        }
        ProjectListResult().apply(func)
    }
}

class ProjectListResult {
    fun loadingStateDisplayed() {
        displayed { id(R.id.progress_view) }
        notDisplayed {
            id(R.id.empty_view)
            id(R.id.error_view)
            id(R.id.recycler_view)
        }
    }

    fun errorStateDisplayed() {
        displayed { id(R.id.error_view) }
        notDisplayed {
            id(R.id.empty_view)
            id(R.id.recycler_view)
            id(R.id.progress_view)
        }
    }

    fun emptyStateDisplayed() {
        displayed { id(R.id.empty_view) }
        notDisplayed {
            id(R.id.recycler_view)
            id(R.id.error_view)
            id(R.id.progress_view)
        }
    }

    fun successStateDisplayed() {
        displayed { id(R.id.recycler_view) }
        notDisplayed {
            id(R.id.empty_view)
            id(R.id.error_view)
            id(R.id.progress_view)
        }
    }

    fun dataHasBeenInvalidated() = emptyStateDisplayed()

    fun projectDetailIsDisplayed() {
        matchIntent {
            className(ProjectActivity::class.java.name)
        }
    }
}
