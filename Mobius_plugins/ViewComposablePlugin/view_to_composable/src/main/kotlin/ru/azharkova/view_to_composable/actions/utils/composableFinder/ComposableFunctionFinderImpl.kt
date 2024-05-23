package ru.azharkova.view_to_composable.actions.utils.composableFinder

import com.intellij.psi.PsiElement
import org.jetbrains.kotlin.psi.*
import ru.azharkova.view_to_composable.actions.utils.isComposable
import org.jetbrains.kotlin.psi.psiUtil.getChildOfType
import ru.azharkova.view_to_composable.actions.utils.isComposableFunction

class ComposableFunctionFinderImpl : ComposableFunctionFinder {

    override fun isFunctionComposable(psiElement: PsiElement): Boolean {
        return when (psiElement) {
            is KtNameReferenceExpression -> psiElement.isComposable()
            is KtNamedFunction -> psiElement.isComposableFunction()
            is KtProperty -> detectComposableFromKtProperty(psiElement)
            is KtValueArgumentList -> {
                val parent = psiElement.parent as? KtCallExpression ?: return false
                parent.isComposable()
            }
            else -> false
        }
    }

    /**
     * To handle both property and property delegates
     */
    private fun detectComposableFromKtProperty(psiElement: KtProperty): Boolean {
        psiElement.getChildOfType<KtCallExpression>().let { propertyChildExpression ->
            return if (propertyChildExpression == null) {
                val propertyDelegate = psiElement.getChildOfType<KtPropertyDelegate>() ?: return false
                val ktCallExpression = propertyDelegate.getChildOfType<KtCallExpression>() ?: return false
                ktCallExpression.isComposable()
            } else {
                propertyChildExpression.isComposable()
            }
        }
    }
}