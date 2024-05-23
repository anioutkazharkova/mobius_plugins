package com.github.azharkova.cleanarch_plugin.services

import com.intellij.openapi.project.Project
import com.github.azharkova.cleanarch_plugin.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
