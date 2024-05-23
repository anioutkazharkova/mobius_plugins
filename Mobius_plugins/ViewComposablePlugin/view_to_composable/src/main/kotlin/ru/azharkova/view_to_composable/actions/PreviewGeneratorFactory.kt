package ru.azharkova.view_to_composable.actions

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import org.jetbrains.kotlin.psi.KtNamedFunction
import ru.azharkova.view_to_composable.actions.utils.AbsPreviewGenerator
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

object PreviewGeneratorFactory {
    fun <T: AbsPreviewGenerator> createGenerators(
            types: List<KClass<out T>>,
            project: Project,
            editor: Editor,
            file: PsiFile,
            sourceFunction: KtNamedFunction
    ): List<T?> {
        return types.map { type ->
            createGenerator(
                    type,
                    project,
                    editor,
                    file,
                    sourceFunction
            )
        }
    }

    private fun <T: AbsPreviewGenerator> createGenerator(
            type: KClass<T>,
            project: Project,
            editor: Editor,
            file: PsiFile,
            sourceFunction: KtNamedFunction
    ): T? {
        return type.primaryConstructor?.call(project, editor, file, sourceFunction)
    }
}