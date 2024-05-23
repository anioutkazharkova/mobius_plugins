package com.github.azharkova.cleanarch_plugin.template.klass

import com.github.azharkova.cleanarch_plugin.manager.PackageManager

fun createrRouter(
    packageName: String = PackageManager.routerPackageName,
    className: String
) = """
    package $packageName
    
    import ${PackageManager.viewPackageName}.I${className}View
    import $packageName.${className.lowercase()}.${className}Activity
    import android.app.Activity
    
    class ${className}Router (
    private val view: I${className}View
    ) : I${className}Router {
    private val activity: ${className}Activity
    get() = view as ${className}Activity
    
    }
    
""".trimIndent()

fun createrIRouter(
    packageName: String = PackageManager.routerPackageName,
    className: String
) = """
    package $packageName
    
    interface I${className}Router
""".trimIndent()