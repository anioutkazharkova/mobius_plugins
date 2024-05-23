package com.github.azharkova.cleanarch_plugin.template

import com.android.tools.idea.wizard.template.Category
import com.android.tools.idea.wizard.template.Constraint
import com.android.tools.idea.wizard.template.FormFactor
import com.android.tools.idea.wizard.template.ModuleTemplateData
import com.android.tools.idea.wizard.template.PackageNameWidget
import com.android.tools.idea.wizard.template.TemplateData
import com.android.tools.idea.wizard.template.TextFieldWidget
import com.android.tools.idea.wizard.template.WizardUiContext
import com.android.tools.idea.wizard.template.impl.defaultPackageNameParameter
import com.android.tools.idea.wizard.template.stringParameter
import com.android.tools.idea.wizard.template.template

val cleanArchActivityTemplate
    get() = template {
      name = "CleanArch Activity + Module data"
      description = "Create files for clean architecture"
      minApi = 21
      category = Category.Other
      formFactor = FormFactor.Mobile
      screens = listOf(WizardUiContext.MenuEntry)

      val packageNameParam = defaultPackageNameParameter
      val classNameParam = stringParameter {
        name = "Class Name"
        default = "Main"
        help = "Use the class name for prefix"
        constraints = listOf(Constraint.NONEMPTY)
      }

      widgets(
        PackageNameWidget(packageNameParam),
        TextFieldWidget(classNameParam)
      )

      recipe = { data: TemplateData ->
        cleanArchActivityTemplate(
          data as ModuleTemplateData,
          packageNameParam.value,
          classNameParam.value
        )
      }
    }