package ru.azharkova.view_to_composable.actions.utils.composableFinder

import com.intellij.psi.PsiElement

interface ComposableFunctionFinder {
    fun isFunctionComposable(psiElement: PsiElement): Boolean
}