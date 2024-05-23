package ru.azharkova.view_to_composable.actions

import com.intellij.codeInsight.intention.LowPriorityAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Iconable
import com.intellij.psi.PsiElement
import ru.azharkova.view_to_composable.icons.SdkIcons.composeIcon
import ru.azharkova.view_to_composable.actions.utils.composableFinder.ComposableFunctionFinder
import ru.azharkova.view_to_composable.actions.utils.composableFinder.ComposableFunctionFinderImpl
import ru.azharkova.view_to_composable.actions.utils.getRootPsiElement.GetRootPsiElement
import ru.azharkova.view_to_composable.actions.utils.isIntentionAvailable
import javax.swing.Icon

class RemoveComposableIntention :
    PsiElementBaseIntentionAction(),
    Iconable,
    LowPriorityAction {

    override fun getText(): String {
        return "Remove this Composable"
    }

    override fun getFamilyName(): String {
        return "Compose helper actions"
    }

    private val composableFunctionFinder: ComposableFunctionFinder = ComposableFunctionFinderImpl()

    private val getRootElement = GetRootPsiElement()

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        return element.isIntentionAvailable(composableFunctionFinder)
    }

    override fun invoke(project: Project, editor: Editor?, element: PsiElement) {
        getRootElement(element.parent)?.delete()
    }

    override fun getIcon(flags: Int): Icon = composeIcon
}