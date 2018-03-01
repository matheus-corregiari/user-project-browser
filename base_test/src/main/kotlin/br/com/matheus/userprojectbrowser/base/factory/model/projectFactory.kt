package br.com.matheus.userprojectbrowser.base.factory.model

import br.com.matheus.userprojectbrowser.sdk.model.DateType
import br.com.matheus.userprojectbrowser.sdk.model.domain.ProjectVO

fun randomProject(): ProjectVO {
    return ProjectVO("", "", "", "", DateType(), DateType())
}

fun projectList(): List<ProjectVO> {
    return arrayListOf(randomProject())
}

fun detailProject(): ProjectVO {
    return ProjectVO(id = "123",
            name = "Project Name",
            logo = "",
            description = "I am the bone of my sword\n" +
                    " Steel is my body and fire is my blood\n" +
                    " I have created over a thousand blades\n" +
                    " Unknown to Death,\n" +
                    " Nor known to Life.\n" +
                    " Have withstood pain to create many weapons\n" +
                    " Yet, those hands will never hold anything\n" +
                    " So as I pray, Unlimited Blade Works.",
            createdOn = DateType(),
            lastChangedOn = DateType())
}
