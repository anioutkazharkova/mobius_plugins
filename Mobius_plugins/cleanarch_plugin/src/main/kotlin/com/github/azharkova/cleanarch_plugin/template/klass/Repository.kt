package com.github.azharkova.cleanarch_plugin.template.klass

import com.github.azharkova.cleanarch_plugin.manager.PackageManager
/*
fun createRepository(
  repositoryPackageName: String = PackageManager.repositoryPackageName,
  domainPackageName: String = PackageManager.domainPackageName,
  className: String
) = """
  package $repositoryPackageName
  
  import $domainPackageName.I${className}Repository
 
  class ${className}Repository(val datasource: I${className}Datasource) : I${className}Repository {
  
  }
""".trimIndent()

fun createIDatasource(
  repositoryPackageName: String = PackageManager.repositoryPackageName,
  domainPackageName: String = PackageManager.domainPackageName,
  className: String
) = """
  package $repositoryPackageName
 
  import $domainPackageName.I${className}Repository
 
  interface I${className}Datasource : I${className}Repository
""".trimIndent() */