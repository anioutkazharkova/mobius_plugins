package com.github.azharkova.cleanarch_plugin.template.klass

import com.github.azharkova.cleanarch_plugin.manager.PackageManager

fun createInteractor(
  domainPackageName: String = PackageManager.domainPackageName,
  className: String
) = """
  package $domainPackageName
  
  import ${PackageManager.presentationPackageName}.I${className}Presenter
  import ${PackageManager.routerPackageName}.I${className}Router
 
  class ${className}Interactor(
  private val presenter: I${className}Presenter,
  private val router: I${className}Router
  ): I${className}Interactor {
  
  }
""".trimIndent()

fun createIInteractor(
  domainPackageName: String = PackageManager.domainPackageName,
  className: String
) = """
  package $domainPackageName
 
  interface I${className}Interactor {
  
  }
""".trimIndent()