package com.nekofar.milad.intellij.turbo.cli

import com.intellij.execution.filters.Filter
import com.intellij.ide.util.projectWizard.SettingsStep
import com.intellij.lang.javascript.boilerplate.NpmPackageProjectGenerator
import com.intellij.lang.javascript.boilerplate.NpxPackageDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentEntry
import com.intellij.openapi.ui.ComboBox
import com.intellij.openapi.ui.LabeledComponent
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.ProjectGeneratorPeer
import com.intellij.util.ui.SwingHelper
import com.nekofar.milad.intellij.turbo.TurboBundle.message
import com.nekofar.milad.intellij.turbo.TurboIcons
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JPanel

class TurboCliProjectGenerator : NpmPackageProjectGenerator() {
    private val packageName = "create-turbo"
    private val npxCommand = "create-turbo"

    override fun getName() = message("turbo.project.generator.name")

    override fun getDescription() = message("turbo.project.generator.description")

    override fun filters(project: Project, baseDir: VirtualFile) = emptyArray<Filter>()

    override fun customizeModule(virtualFile: VirtualFile, contentEntry: ContentEntry?) {}

    override fun createPeer(): ProjectGeneratorPeer<Settings> {
        val examples = mapOf(
            Pair("basic", "Next.js"),
            Pair("with-svelte", "SvelteKit"),
            Pair("design-system", "Design System"),
            Pair("with-gatsby", "Gatsby.js"),
            Pair("kitchen-sink", "Kitchen Sink"),
            Pair("with-react-native-web", "React Native"),
            Pair("with-create-react-app", "Create React App"),
            Pair("with-docker", "Docker"),
            Pair("with-changesets", "Monorepo with Changesets"),
            Pair("non-monorepo", "Non-Monorepo"),
            Pair("with-prisma", "Prisma"),
            Pair("with-rollup", "Rollup"),
            Pair("with-tailwind", "Tailwind CSS"),
            Pair("with-vite", "Vite"),
        )
        val example = ComboBox(examples.keys.toTypedArray())

        return object : NpmPackageGeneratorPeer() {
            override fun buildUI(settingsStep: SettingsStep) {
                super.buildUI(settingsStep)
                settingsStep.addSettingsField(
                    message("turbo.project.generator.example"),
                    example
                )
            }

            override fun getSettings(): Settings {
                val settings = super.getSettings()
                /*settings.putUserData(
                    typeScriptKey,
                    typeScriptCheckbox.isSelected
                )*/
                return settings
            }

            override fun createPanel(): JPanel {
                val panel = super.createPanel()
                panel.add(
                    createLabeledComponent(
                        message("turbo.project.generator.example"),
                        example
                    )
                )
                updateExamples()
                return panel
            }

            private fun createLabeledComponent(text: String, comp: JComponent): LabeledComponent<*> {
                val component = LabeledComponent.create(comp, text)
                component.labelLocation = BorderLayout.WEST
                return component
            }

            private fun updateExamples() {
                SwingHelper.updateItems(
                    example,
                    examples.keys.toList(),
                    examples.keys.first()
                )
            }
        }
    }

    override fun packageName(): String = packageName

    override fun presentablePackageName() = message("turbo.project.generator.presentable.package.name")

    override fun getNpxCommands() = listOf(NpxPackageDescriptor.NpxCommand(packageName, npxCommand))

    override fun generateInTemp() = true

    override fun generatorArgs(project: Project, baseDir: VirtualFile) = arrayOf(project.name)

    override fun getIcon() = TurboIcons.ProjectGenerator
}
