package com.github.azharkova.cleanarch_plugin.manager


import com.github.azharkova.cleanarch_plugin.manager.Path.PRESENTATION

fun addPackageName(packageName: String, applicationPackageName: String, className: String) =
  PackageManager.setPackageName(packageName, applicationPackageName, className)

object PackageManager {

  private var _packageName: String = ""
  val packageName by lazy { _packageName }

  private var _applicationPackageName: String = ""
  val applicationPackageName by lazy { _applicationPackageName }

  private var _className: String = ""
  val className by lazy { _className}

  val presentationPackageName by lazy { toModulePackageName(PRESENTATION.name)}
  val routerPackageName by lazy { toModulePackageName(Path.ROUTER.name)}
  val viewPackageName by lazy { toModulePackageName(Path.VIEW.name)}
  val configPackageName by lazy { toModulePackageName(Path.CONFIG.name) }
    val domainPackageName by lazy { toModulePackageName(Path.DOMAIN.name) }

  fun setPackageName(packageName: String, applicationPackageName: String, className: String) {
    this._packageName = packageName
    this._applicationPackageName = applicationPackageName
    this._className = className
  }

  private fun toModulePackageName(moduleName: String): String {
   // val defaultPackage = _packageName

    return _packageName
      .replace("ui", "")
      .split(".").let {
        val pre = it.subList(0, it.lastIndex)
        val mid = listOf(moduleName.toLowerCase())
        val last = it.last()
        (pre + last + className.lowercase() + mid).joinToString(".")
      }
  }
}