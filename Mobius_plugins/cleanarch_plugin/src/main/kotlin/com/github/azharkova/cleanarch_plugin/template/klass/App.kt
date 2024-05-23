package com.github.azharkova.cleanarch_plugin.template.klass

import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.RecipeExecutor
import com.android.tools.idea.wizard.template.impl.activities.common.generateSimpleLayout
import com.github.azharkova.cleanarch_plugin.manager.PackageManager
import com.github.azharkova.cleanarch_plugin.template.utils.toSnakeCase
import com.github.azharkova.cleanarch_plugin.template.workaround.manifestTemplateXml
import java.io.File

fun RecipeExecutor.createActivity(
  packageName: String = PackageManager.packageName,
  applicationPackageName: String = PackageManager.applicationPackageName,
  className: String,
  manifestOut: File,
  moduleData: ModuleTemplateData
): String {
  val activityClassName = "${className}Activity"
  val layoutFileName = "Activity${className}"
  mergeXml(manifestTemplateXml("$packageName.${className.lowercase()}", activityClassName), manifestOut.resolve("AndroidManifest.xml"))
  generateSimpleLayout(moduleData, activityClassName, layoutFileName.toSnakeCase())
  return createActivity(packageName, applicationPackageName, className)
}

fun createActivity(
  packageName: String,
  applicationPackageName: String,
  className: String
) = """
  package ${packageName}.${className.lowercase()}
  
  import androidx.activity.viewModels
  import $packageName.databinding.Activity${className}Binding
  import $packageName.base.BaseActivity
  import ${PackageManager.viewPackageName}.I${className}View
  import ${PackageManager.domainPackageName}.I${className}Interactor
  import ${packageName}.${className.lowercase()}.${className}ViewModel
  
  class ${className}Activity : BaseActivity<Activity${className}Binding>(), I${className}View {
  
    override var interactor: I${className}Interactor? = null 
  
    private val viewModels by viewModels<${className}ViewModel>()
  
    override fun inflateBinding() = Activity${className}Binding.inflate(layoutInflater)
    
  }
""".trimIndent()

fun createView(packageName: String,
               applicationPackageName: String,
               className: String

) = """
  package $packageName
  
  interface I${className}View {
    
  }
""".trimIndent()