package com.github.azharkova.cleanarch_plugin.template.klass

import com.github.azharkova.cleanarch_plugin.manager.PackageManager

fun createPresentation(
  presentationPackageName: String = PackageManager.presentationPackageName,
  className: String
) = """
  package $presentationPackageName
  
  import ${PackageManager.viewPackageName}.I${className}View
  
  class ${className}Presenter(
      private val view: I${className}View
      ): I${className}Presenter {
      
  }
""".trimIndent()

fun createIPresentation(
  presentationPackageName: String = PackageManager.presentationPackageName,
  className: String
) = """
  package $presentationPackageName
  
  interface I${className}Presenter {
  
  }
""".trimIndent()


fun createViewModel(
  packageName: String = PackageManager.packageName,
  className: String
) = """
  package $packageName.${className.lowercase()}
  
  import androidx.lifecycle.ViewModel
 
  data class ${className}ViewState(
   val type: ${className}ViewStateType = ${className}ViewStateType.INIT
  )
  
  enum class ${className}ViewStateType{
   INIT
  }
 
  class ${className}ViewModel : ViewModel() {
  
  }
""".trimIndent()