package com.github.azharkova.cleanarch_plugin

import com.android.tools.idea.wizard.template.Template
import com.android.tools.idea.wizard.template.WizardTemplateProvider
import com.github.azharkova.cleanarch_plugin.template.cleanArchActivityTemplate

class WizardTemplateProviderImpl : WizardTemplateProvider() {
  override fun getTemplates(): List<Template> = listOf(cleanArchActivityTemplate)
}