package ru.azharkova.view_to_composable.actions.wrapActions

import com.intellij.codeInsight.template.impl.TemplateImpl
import com.intellij.codeInsight.template.impl.TemplateSettings
import ru.azharkova.view_to_composable.actions.wrapActions.BaseWrapWithComposableAction

class WrapWithBoxIntention : BaseWrapWithComposableAction() {

    override fun getText(): String {
        return "Wrap with Box"
    }

    override fun getTemplate(): TemplateImpl? {
        return TemplateSettings.getInstance().getTemplate("boxcomp", "ComposeHelperTemplate")
    }
}

class WrapWithCardIntention : BaseWrapWithComposableAction() {

    override fun getText(): String {
        return "Wrap with Card"
    }

    override fun getTemplate(): TemplateImpl? {
        return TemplateSettings.getInstance().getTemplate("cardcomp", "ComposeHelperTemplate")
    }
}

class WrapWithColumnIntention : BaseWrapWithComposableAction() {

    override fun getText(): String {
        return "Wrap with Column"
    }

    override fun getTemplate(): TemplateImpl? {
        return TemplateSettings.getInstance().getTemplate("columncomp", "ComposeHelperTemplate")
    }
}

class WrapWithRowIntention : BaseWrapWithComposableAction() {

    override fun getText(): String {
        return "Wrap with Row"
    }

    override fun getTemplate(): TemplateImpl? {
        return TemplateSettings.getInstance().getTemplate("rowcomp", "ComposeHelperTemplate")
    }
}

class WrapWithLzyColumnIntention : BaseWrapWithComposableAction() {

    override fun getText(): String {
        return "Wrap with LazyColumn"
    }

    override fun getTemplate(): TemplateImpl? {
        return TemplateSettings.getInstance().getTemplate("lazycolumncomp", "ComposeHelperTemplate")
    }
}

class WrapWithLzyRowIntention : BaseWrapWithComposableAction() {

    override fun getText(): String {
        return "Wrap with LazyRow"
    }

    override fun getTemplate(): TemplateImpl? {
        return TemplateSettings.getInstance().getTemplate("lazyrowcomp", "ComposeHelperTemplate")
    }
}