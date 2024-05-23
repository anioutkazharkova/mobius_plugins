package com.github.azharkova.cleanarch_plugin.manager

import com.github.azharkova.cleanarch_plugin.manager.Path.*
import com.github.azharkova.cleanarch_plugin.manager.PathConstants.appPath
import com.github.azharkova.cleanarch_plugin.manager.PathConstants.configuratorPath
import com.github.azharkova.cleanarch_plugin.manager.PathConstants.domainPath
import com.github.azharkova.cleanarch_plugin.manager.PathConstants.presentationPath
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ProjectRootManager
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiManager

class ProjectFileManager(
  private val project: Project
) {

  private var modularized = false
  private val virtualFiles = ProjectRootManager.getInstance(project).contentSourceRoots

  private val relativePaths = listOf(
    appPath, presentationPath,  domainPath, configuratorPath
  )

  private val resources = mutableListOf<Triple<Path, PsiDirectory?, String>>(
    Triple(APP, null, appPath),
    Triple(PRESENTATION, null, presentationPath),
    Triple(CONFIG, null, configuratorPath),
    //Triple(REPOSITORY, null, repositoryPath),
    //Triple(DATASOURCE, null, datasourcePath),
    Triple(DOMAIN, null, domainPath)
  )

  fun dirOf(path: Path) = if (modularized) resources[path.type].second!! else resources[0].second!!

  fun init(modularized: Boolean = false): Boolean {
    relativePaths.forEachIndexed { idx, path ->
      virtualFile(virtualFiles, path)?.let {
        resources[idx] = resources[idx].copy(second = PsiManager.getInstance(project).findDirectory(it))
      }
    }
    resources.forEach { println(it.second) }
    if (modularized and resources.any { it.second == null }) return false
    return true
  }

  private fun virtualFile(virtualFiles: Array<VirtualFile>, relativePath: String) =
    virtualFiles.firstOrNull { it.path.contains(relativePath) }
}