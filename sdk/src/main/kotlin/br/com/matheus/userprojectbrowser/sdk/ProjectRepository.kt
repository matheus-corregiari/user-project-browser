package br.com.matheus.userprojectbrowser.sdk

import br.com.matheus.userprojectbrowser.sdk.data.remote.apiInstance

object ProjectRepository {

    private val allProjectsLiveData = apiInstance.listProjects()

    fun listAllProjects() = allProjectsLiveData

}