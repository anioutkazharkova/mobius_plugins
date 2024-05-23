package ru.azharkova.view_to_composable.actions

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import ru.azharkova.view_to_composable.actions.utils.AbsPreviewGenerator

class SimplePreviewGenerator(
        project: Project,
        editor: Editor,
        file: PsiFile,
        sourceFunction: KtNamedFunction,
) : AbsPreviewGenerator("Create Simple Preview", project, editor, file, sourceFunction) {

    override fun actionPerformed(e: AnActionEvent) {
        this.writePreview("")
    }

}