package com.github.azharkova.cleanarch_plugin.template

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.addAllKotlinDependencies
import com.github.azharkova.cleanarch_plugin.listeners.MyProjectManagerListener.Companion.projectInstance
import com.github.azharkova.cleanarch_plugin.manager.PackageManager
import com.github.azharkova.cleanarch_plugin.manager.Path.APP
import com.github.azharkova.cleanarch_plugin.manager.ProjectFileManager
import com.github.azharkova.cleanarch_plugin.manager.addPackageName
import com.github.azharkova.cleanarch_plugin.template.klass.*
import com.github.azharkova.cleanarch_plugin.template.utils.asKt
import com.github.azharkova.cleanarch_plugin.template.utils.save

fun RecipeExecutor.cleanArchActivityTemplate(
  moduleData: ModuleTemplateData,
  packageName: String,
  className: String
) {
  val (projectData, _, _, manifestOut) = moduleData
  val project = projectInstance ?: return

  addAllKotlinDependencies(moduleData)
  addPackageName(packageName, projectData.applicationPackage.toString(), className)

  val pfm = ProjectFileManager(project)
  if (pfm.init().not()) return

  createActivity(className = className, manifestOut = manifestOut, moduleData = moduleData)
    .save(pfm.dirOf(APP), "$packageName.${className.lowercase()}", "${className}Activity".asKt())

  createViewModel(className = className)
    .save(pfm.dirOf(APP), "$packageName.${className.lowercase()}", "${className}ViewModel".asKt())

  createPresentation(className = className)
    .save(pfm.dirOf(APP), PackageManager.presentationPackageName, "${className}Presenter".asKt())
  createIPresentation(className = className)
    .save(pfm.dirOf(APP), PackageManager.presentationPackageName, "I${className}Presenter".asKt())
createConfigurator(className = className).save(pfm.dirOf(APP), PackageManager.configPackageName,
  "${className}Config".asKt())
  createInteractor(className = className)
    .save(pfm.dirOf(APP), PackageManager.domainPackageName, "${className}Interactor".asKt())
  createIInteractor(className = className)
    .save(pfm.dirOf(APP), PackageManager.domainPackageName, "I${className}Interactor".asKt())
  createrRouter(className = className)
    .save(pfm.dirOf(APP), PackageManager.routerPackageName, "${className}Router".asKt())
  createrIRouter(className = className)
    .save(pfm.dirOf(APP), PackageManager.routerPackageName, "I${className}Router".asKt())
  createView(className = className)
    .save(pfm.dirOf(APP), PackageManager.viewPackageName, "I${className}View".asKt())
}