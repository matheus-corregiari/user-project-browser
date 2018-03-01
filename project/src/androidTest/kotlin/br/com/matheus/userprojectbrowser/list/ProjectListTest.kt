package br.com.matheus.userprojectbrowser.list

import android.support.test.runner.AndroidJUnit4
import br.com.matheus.userprojectbrowser.base.BaseActivityTest
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProjectListTest : BaseActivityTest<ProjectListActivity>(ProjectListActivity::class) {

    @Test
    fun whenReceiveLoading_shouldDisplayLoadingState() {
        projectList {
            launchActivity()
        } receiveLoading {
            loadingStateDisplayed()
        }
    }

    @Test
    fun whenReceiveError_shouldDisplayErrorState() {
        projectList {
            launchActivity()
        } receiveError {
            errorStateDisplayed()
        }
    }

    @Test
    fun whenReceiveEmpty_shouldDisplayEmptyState() {
        projectList {
            launchActivity()
        } receiveEmpty {
            emptyStateDisplayed()
        }
    }

    @Test
    fun whenReceiveList_shouldDisplaySuccessState() {
        projectList {
            launchActivity()
        } receiveList {
            successStateDisplayed()
        }
    }

    @Test
    fun onErrorState_clickOnRetry_shouldInvalidateData() {
        projectList {
            onErrorState()
        } clickOnRetry {
            dataHasBeenInvalidated()
        }
    }

    @Test
    fun onSuccessState_clickOnItem_shouldOpenProjectDetail() {
        projectList {
            onSuccessState()
        } clickOnItem {
            projectDetailIsDisplayed()
        }
    }
}
