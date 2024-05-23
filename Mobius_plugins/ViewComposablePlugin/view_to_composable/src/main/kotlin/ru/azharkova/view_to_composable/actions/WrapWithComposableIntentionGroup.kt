package ru.azharkova.view_to_composable.actions

import com.intellij.codeInsight.intention.impl.IntentionActionGroup
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.ListPopup
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.openapi.util.Iconable
import com.intellij.psi.PsiFile
import com.intellij.ui.popup.list.ListPopupImpl
import ru.azharkova.view_to_composable.actions.wrapActions.BaseWrapWithComposableAction
import ru.azharkova.view_to_composable.actions.wrapActions.WrapWithBoxIntention
import ru.azharkova.view_to_composable.actions.wrapActions.WrapWithCardIntention
import ru.azharkova.view_to_composable.actions.wrapActions.WrapWithColumnIntention
import ru.azharkova.view_to_composable.actions.wrapActions.WrapWithLzyColumnIntention
import ru.azharkova.view_to_composable.actions.wrapActions.WrapWithLzyRowIntention
import ru.azharkova.view_to_composable.actions.wrapActions.WrapWithRowIntention
import javax.swing.Icon
import ru.azharkova.view_to_composable.icons.SdkIcons

class WrapWithComposableIntentionGroup :
    IntentionActionGroup<BaseWrapWithComposableAction>(
        listOf(
            WrapWithBoxIntention(),
            WrapWithCardIntention(),
            WrapWithColumnIntention(),
            WrapWithRowIntention(),
            WrapWithLzyColumnIntention(),
            WrapWithLzyRowIntention()
        )
    ),
    Iconable {

    private fun createPopup(
        project: Project,
        actions: List<BaseWrapWithComposableAction>,
        invokeAction: (BaseWrapWithComposableAction) -> Unit
    ): ListPopup {

        val step = object : BaseListPopupStep<BaseWrapWithComposableAction>(null, actions) {

            override fun getTextFor(action: BaseWrapWithComposableAction) = action.text

            override fun onChosen(selectedValue: BaseWrapWithComposableAction, finalChoice: Boolean): PopupStep<*>? {
                invokeAction(selectedValue)
                return FINAL_CHOICE
            }
        }

        return ListPopupImpl(project, step)
    }

    override fun getFamilyName(): String {
        return "Compose helper actions"
    }

    override fun chooseAction(
        project: Project,
        editor: Editor,
        file: PsiFile,
        actions: List<BaseWrapWithComposableAction>,
        invokeAction: (BaseWrapWithComposableAction) -> Unit
    ) {
        createPopup(project, actions, invokeAction).showInBestPositionFor(editor)
    }

    override fun getGroupText(actions: List<BaseWrapWithComposableAction>): String {
        return "Wrap with Composable"
    }

    override fun getIcon(flags: Int): Icon = SdkIcons.composeIcon
}