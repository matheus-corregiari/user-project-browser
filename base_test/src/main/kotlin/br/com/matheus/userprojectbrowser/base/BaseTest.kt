package br.com.matheus.userprojectbrowser.base

import android.content.Intent
import android.support.test.espresso.intent.rule.IntentsTestRule
import br.com.matheus.userprojectbrowser.TestActivity
import br.com.matheus.userprojectbrowser.base.extension.mockRepository
import br.com.matheus.userprojectbrowser.sdk.ProjectRepository
import org.junit.Before
import org.junit.Rule
import kotlin.reflect.KClass

open class BaseActivityTest<AC : BaseActivity>(activityClass: KClass<AC>) {

    @Rule
    @JvmField
    val rule = IntentsTestRule(activityClass.java, true, false)

    @Before
    fun setup() {
        mockRepository(ProjectRepository::class.java)
    }

}

open class BaseFragmentTest : BaseActivityTest<TestActivity>(TestActivity::class) {
    @Before
    fun launchActivity() {
        rule.launchActivity(Intent())
    }
}

open class BaseViewTest : BaseActivityTest<TestActivity>(TestActivity::class) {
    @Before
    fun launchActivity() {
        rule.launchActivity(Intent())
    }
}