package com.github.azharkova.cleanarch_plugin.template.klass

import com.github.azharkova.cleanarch_plugin.manager.PackageManager

fun createConfigurator(
    packageName: String = PackageManager.configPackageName,
    className: String
) =
    """
        package $packageName
        
       import ${PackageManager.presentationPackageName}.${className}Presenter
       import ${PackageManager.routerPackageName}.${className}Router
       import ${PackageManager.domainPackageName}.${className}Interactor
       import ${PackageManager.domainPackageName}.I${className}Interactor
        
        class ${className}Config : IConfigurator {
        override fun create(view: IView): IInteractor? {
        val presenter = ${className}Presenter(view = view)
        val router = ${className}Router(view = view)
        val interactor: I${className}Interactor = ${className}Interactor(
            presenter = presenter,
            router = router
            )
       
        view.interactor = interactor
        
        return interactor
    }
        }
    """.trimIndent()