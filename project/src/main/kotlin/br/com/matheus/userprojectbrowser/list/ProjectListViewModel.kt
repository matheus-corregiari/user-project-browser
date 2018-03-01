package br.com.matheus.userprojectbrowser.list

import br.com.matheus.userprojectbrowser.base.BaseViewModel
import br.com.matheus.userprojectbrowser.sdk.ProjectRepository

class ProjectListViewModel : BaseViewModel() {

    val projectListLiveData = ProjectRepository.listAllProjects()

}
