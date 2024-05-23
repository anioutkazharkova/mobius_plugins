package ru.azharkova.view_to_composable.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.LangDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.project.Project
import com.intellij.psi.*
import com.intellij.refactoring.suggested.startOffset
import com.intellij.testFramework.utils.editor.saveToDisk
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtPsiFactory
import ru.azharkova.view_to_composable.actions.utils.capitalizeFirstLetter
import ru.azharkova.view_to_composable.actions.utils.lowFirstLetter


class ViewToComposableGen : AnAction("View to Composable") {
    private var packageName: String = ""

    override fun beforeActionPerformedUpdate(action: AnActionEvent) {
        val psiClass = getPsiClassFromContext(action) ?: return
        val editor = action.getData(PlatformDataKeys.EDITOR)
        val isToComposable = psiClass.annotations.any {
            it.text.contains("ToComposable")
        }
        if (!isToComposable || editor == null) {
            return
        }

        val (params, applyData) = prepareParams(psiClass)

        Generator(
            psiClass.project,
            editor,
            psiClass.containingFile,
            psiClass.name.orEmpty(),
            params,
            applyData,
            element = psiClass.originalElement,
        ).writePreview("")
    }

    private fun prepareParams(psiClass: PsiClass): Pair<String, String> {
        var params = ""
        var applyData = ""
        val voidType = "(()->Unit)"

        psiClass.methods.forEach {
            val isPublic = !it.modifierList.hasModifierProperty(PsiModifier.PRIVATE)
            if (it.isSetter() && isPublic) {
                val shortName = it.shortName()
                val hasGetter = psiClass.methods.any {
                    it.isGetter() && it.shortName() == shortName
                }
                val type = if (it.parameters.isNullOrEmpty()) {
                    if (it.isClickModifier()) {
                        voidType
                    } else ""
                } else {
                    it.parameters.first().type.toString().replace("PsiType:", "")
                }
                if (!hasGetter) {

                    params += "$shortName: $type,\n"
                    applyData += if (type != voidType) {
                        "${it.name}($shortName)\n"
                    } else {
                        """
                                    ${it.name}().apply{
                                       $shortName.invoke()
                                    }
                                """.trimMargin() + "\n"
                    }
                } else {
                    params += "${it.shortName()}: $type,\n"
                    applyData += "this.${it.shortName()} = ${it.shortName()}\n"
                }
            }
        }
        return Pair(params, applyData)
    }

    private fun prepareImports(file: PsiFile): List<String> {
        val imports = (file as? KtFile)?.importDirectives.orEmpty().map { it.importedFqName.toString() }
        val newImports = buildList {
            add("androidx.compose.runtime.Composable")
            add("androidx.compose.ui.Modifier")
            add("androidx.compose.ui.viewinterop.AndroidView")
        }
        return newImports.filter {
            !imports.contains(it)
        }
    }

    override fun actionPerformed(action: AnActionEvent) {}

    private fun getPsiClassFromContext(e: AnActionEvent): PsiClass? {
        val psiFile = e.getData(LangDataKeys.PSI_FILE)
        val editor = e.getData(PlatformDataKeys.EDITOR)
        packageName = "package " + (psiFile as? KtFile)?.packageFqName.toString()

        if (psiFile == null || editor == null) {
            return null
        }
        val data = ((psiFile as? KtFile)?.classes as? Array<out PsiClass>)


        val offset = editor.caretModel.offset
        val element = psiFile.findElementAt(offset) ?: return null
        return data?.firstOrNull { it.name == element.text }
    }

    class Generator(
        private val project: Project,
        private val editor: Editor,
        private val file: PsiFile,
        private val sourceFunctionName: String,
        private val params: String,
        private val applyData: String,
        private val element: PsiElement,
    ) {

        fun writePreview(previewParameters: String) {
            val newFunctionName = sourceFunctionName.capitalizeFirstLetter() + "Composable"

            var functionBody = "@Composable\n"
            if (previewParameters.isNotEmpty()) {
                functionBody += "(\n$previewParameters\n)"
            }

            functionBody += """
            fun $newFunctionName(
            $params
            modifier: Modifier = Modifier,
            viewConfig: ()->Unit
            ) {
            AndroidView (
                    factory = { context -> 
                    $sourceFunctionName(context = context)
                    },
                    modifier = modifier,
                    update = { view ->
                        view.apply {
                        $applyData
                        }
                        viewConfig()
                    }
                )
           }   
           """.trimMargin() + "\n\n"

            WriteCommandAction.writeCommandAction(project, file).run<Throwable> {
                val factory = KtPsiFactory(project)
                val newFunction = factory.createFunction(functionBody)
                editor.document.insertString(
                    element.startOffset,
                    newFunction.text + "\n\n"
                )
                editor.document.saveToDisk()
                editor.scrollingModel.scrollToCaret(ScrollType.CENTER)
            }
        }
    }
}

fun PsiMethod.isGetter(): Boolean {
    return this.name.startsWith("get")
}

fun PsiMethod.isSetter(): Boolean {
    return this.name.startsWith("set")
}

fun PsiMethod.isClickModifier(): Boolean {
    return this.annotations.any { it.text.contains("ClickModifier") }
}

fun PsiMethod.isPropertyModifier(): Boolean {
    return this.annotations.any { it.text.contains("PropertyModifier") }
}

fun PsiMethod.shortName(): String {
    return this.name.replace("get", "").replace("set", "").lowFirstLetter()
}