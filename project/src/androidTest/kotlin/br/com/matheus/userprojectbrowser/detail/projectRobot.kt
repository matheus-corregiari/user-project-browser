package br.com.matheus.userprojectbrowser.detail

import android.content.Intent
import android.support.test.rule.ActivityTestRule
import br.com.concretesolutions.kappuccino.assertions.VisibilityAssertions.displayed
import br.com.matheus.userprojectbrowser.R
import br.com.matheus.userprojectbrowser.base.BaseActivityRobot
import br.com.matheus.userprojectbrowser.base.extension.fullDate
import br.com.matheus.userprojectbrowser.base.factory.model.detailProject
import br.com.matheus.userprojectbrowser.sdk.model.domain.ProjectVO

fun ProjectTest.project(func: ProjectRobot.() -> Unit): ProjectRobot {
    return ProjectRobot(rule, detailProject()).apply {
        launchActivity(Intent().putExtra("EXTRA_PROJECT", project))
        func()
    }
}

class ProjectRobot(rule: ActivityTestRule<ProjectActivity>,
                   val project: ProjectVO)
    : BaseActivityRobot<ProjectActivity>(rule) {

    fun iconIsDisplayed() {
        displayed {
            id(R.id.project_icon)
        }
    }

    fun nameIsDisplayed() {
        displayed {
            allOf {
                id(R.id.project_name)
                text(project.name)
            }
        }
    }

    fun descriptionIsDisplayed() {
        displayed {
            allOf {
                id(R.id.project_description)
                text(project.description)
            }
        }
    }

    fun createDateIsDisplayed() {
        displayed {
            allOf {
                id(R.id.create_date)
                text(project.createdOn.fullDate())
            }
        }
    }

    fun lastUpdateDateIsDisplayed() {
        displayed {
            allOf {
                id(R.id.last_update_date)
                text(project.lastChangedOn.fullDate())
            }
        }
    }

}