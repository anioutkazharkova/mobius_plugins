package ru.azharkova.view_to_composable.actions.utils

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.refactoring.suggested.startOffset
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.kotlin.psi.KtPsiFactory

abstract class AbsPreviewGenerator(
    name: String,
    private val project: Project,
    private val editor: Editor,
    private val file: PsiFile,
    private val sourceFunction: KtNamedFunction
): AnAction(name) {
    private fun getPositionOfPreview(): KtNamedFunction? {
        val methods = file.children.filterIsInstance<KtNamedFunction>()
        val methodNames = methods.map { it.name }

        val startIndex = methods.indexOf(sourceFunction)
        for (i in startIndex downTo 0) {
            val previousFunction = methods[i]

            if (methodNames.contains("Preview${previousFunction.name?.capitalizeFirstLetter()}")) {
                return methods[methodNames.indexOf("Preview${previousFunction.name?.capitalizeFirstLetter()}")]
            }
        }

        return null
    }

    private fun getFirstPreviewOfFile(): KtNamedFunction? {
        val methods = file.children.filterIsInstance<KtNamedFunction>()
        val previews = methods.filter { it.name?.startsWith("Preview") ?: false }

        return if (previews.isEmpty()) null else methods[methods.indexOf(previews[0])]
    }

    fun writePreview(previewParameters: String) {
        val sourceFunctionName = sourceFunction.name
        val newFunctionName = "Preview" + sourceFunctionName?.capitalizeFirstLetter()

        var functionBody = "@Preview"
        if (previewParameters.isNotEmpty()) {
            functionBody += "(\n$previewParameters\n)"
        }

        functionBody += "\n@Composable\nfun $newFunctionName() {\n$sourceFunctionName()\n}"

        WriteCommandAction.writeCommandAction(project, file).run<Throwable> {
            val factory = KtPsiFactory(project)

            val newFunction = file.add(factory.createFunction(functionBody))

            editor.caretModel.primaryCaret.moveToOffset(newFunction.startOffset + functionBody.length - 3)
            editor.scrollingModel.scrollToCaret(ScrollType.CENTER)
        }
    }
}