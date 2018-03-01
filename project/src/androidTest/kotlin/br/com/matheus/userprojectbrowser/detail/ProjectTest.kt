package br.com.matheus.userprojectbrowser.detail

import android.support.test.runner.AndroidJUnit4
import br.com.matheus.userprojectbrowser.base.BaseActivityTest
import br.com.matheus.userprojectbrowser.detail.ProjectActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectTest : BaseActivityTest<ProjectActivity>(ProjectActivity::class) {

    @Test
    fun whenEnter_shouldDisplayProjectInfo() {
        project {
            iconIsDisplayed()
            nameIsDisplayed()
            descriptionIsDisplayed()
            createDateIsDisplayed()
            lastUpdateDateIsDisplayed()
        }
    }

}
