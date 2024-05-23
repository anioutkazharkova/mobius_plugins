package ru.azharkova.view_to_composable.actions.utils

import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFileFactory
import org.jetbrains.kotlin.idea.KotlinLanguage

fun String.capitalizeFirstLetter(): String = if (isNotEmpty()) { this[0].toUpperCase() + this.drop(1) } else { "" }

fun String.asKt() = "${this.capitalize()}.kt"

val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()
fun String.toSnakeCase() = camelRegex.replace(this) { "_${it.value}" }.toLowerCase()



fun String.lowFirstLetter(): String = if (isNotEmpty()) { this[0].toLowerCase() + this.drop(1) } else { "" }
