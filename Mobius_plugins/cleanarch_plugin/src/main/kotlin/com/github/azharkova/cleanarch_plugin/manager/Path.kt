package com.github.azharkova.cleanarch_plugin.manager

enum class Path(val type: Int) {
  APP(0), PRESENTATION(1), DOMAIN(2), ROUTER(3), VIEW(4), CONFIG(5);

  companion object {
    private val map = values().associateBy(Path::type)
    fun from(type: Int) = map[type]
  }
}