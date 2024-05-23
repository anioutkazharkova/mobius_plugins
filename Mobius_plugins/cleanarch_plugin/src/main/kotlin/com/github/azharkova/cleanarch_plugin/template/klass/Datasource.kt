package com.github.azharkova.cleanarch_plugin.template.klass
/*
import com.github.azharkova.cleanarch_plugin.manager.PackageManager

fun createDatasource(
  datasourcePackageName: String = PackageManager.datasourcePackageName,
  repositoryPackageName: String = PackageManager.repositoryPackageName,
  className: String
) = """
  package $datasourcePackageName
  
  import $repositoryPackageName.I${className}Datasource
  
  class ${className}Datasource : I${className}Datasource {
  
  }
""".trimIndent()

*/