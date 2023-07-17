package com.nekofar.milad.intellij.turbo.cli

import com.intellij.execution.filters.Filter
import com.intellij.lang.javascript.boilerplate.NpmPackageProjectGenerator
import com.intellij.lang.javascript.boilerplate.NpxPackageDescriptor
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentEntry
import com.intellij.openapi.vfs.VirtualFile
import com.nekofar.milad.intellij.turbo.TurboBundle.message
import com.nekofar.milad.intellij.turbo.TurboIcons

class TurboCliProjectGenerator : NpmPackageProjectGenerator() {
    private val packageName = "create-turbo"
    private val npxCommand = "create-turbo"

    override fun getName() = message("turbo.project.generator.name")

    override fun getDescription() = message("turbo.project.generator.description")

    override fun filters(project: Project, baseDir: VirtualFile) = emptyArray<Filter>()

    override fun customizeModule(virtualFile: VirtualFile, contentEntry: ContentEntry?) {}

    override fun packageName(): String = packageName

    override fun presentablePackageName() = message("turbo.project.generator.presentable.package.name")

    override fun getNpxCommands() = listOf(NpxPackageDescriptor.NpxCommand(packageName, npxCommand))

    override fun generateInTemp() = true

    override fun generatorArgs(project: Project, baseDir: VirtualFile) = arrayOf(project.name)

    override fun getIcon() = TurboIcons.ProjectGenerator
}
