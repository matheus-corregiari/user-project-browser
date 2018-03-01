package br.com.matheus.userprojectbrowser.sdk

import br.com.matheus.userprojectbrowser.sdk.data.remote.apiInstance

object ProjectRepository {

    fun listAllProjects() = apiInstance.listProjects()

}