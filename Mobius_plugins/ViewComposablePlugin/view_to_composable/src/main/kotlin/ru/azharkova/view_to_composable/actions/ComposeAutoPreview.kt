package ru.azharkova.view_to_composable.actions
import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import org.jetbrains.kotlin.psi.KtNamedFunction

class ComposeAutoPreview: PsiElementBaseIntentionAction(), IntentionAction {
    override fun getText(): String = "Generate Compose Preview"
    override fun getFamilyName(): String = text

    override fun isAvailable(project: Project, editor: Editor?, element: PsiElement): Boolean {
        val sourceFunction =
                element as? KtNamedFunction ?: PsiTreeUtil.getParentOfType(element, KtNamedFunction::class.java)
                ?: return false

        var isComposable = false
        var isPreview = false
        sourceFunction.annotationEntries.forEach {
            if (it.text.contains("Composable"))
                isComposable = true

            if (it.text.contains("Preview"))
                isPreview = true
        }

        return isComposable && !isPreview
    }

    override fun invoke(project: Project, editor: Editor, element: PsiElement) {
        val sourceFunction =
                element as? KtNamedFunction ?: PsiTreeUtil.getParentOfType(element, KtNamedFunction::class.java)
                ?: return

        SimplePreviewGenerator(
            project,
            editor,
            element.containingFile,
            sourceFunction
        ).writePreview("")
    }
}