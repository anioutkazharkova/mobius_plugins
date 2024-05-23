package com.github.azharkova.cleanarch_plugin.template.klass

import com.github.azharkova.cleanarch_plugin.manager.PackageManager

fun createView(
    packageName: String = PackageManager.viewPackageName,
    className: String
) = """
    package $packageName
    
    import ${PackageManager.domainPackageName}.I${className}Interactor

    interface I${className}View {
        var interactor: I${className}Interactor?
    }
"""