package com.github.runtimeexcpetion.copyaireference.actions

import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import java.awt.datatransfer.StringSelection

class CopyAiReferenceAction : AnAction() {

    override fun getActionUpdateThread(): ActionUpdateThread {
        return ActionUpdateThread.BGT
    }

    override fun update(e: AnActionEvent) {
        val project = e.project
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE)
        val editor = e.getData(CommonDataKeys.EDITOR)

        val projectViewValid = project != null && file != null && !file.isDirectory
        val editorValid = project != null && editor != null

        e.presentation.isEnabledAndVisible = projectViewValid || editorValid
    }

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val file = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return
        val result = buildReferenceString(project, file, e.getData(CommonDataKeys.EDITOR)) ?: return
        CopyPasteManager.getInstance().setContents(StringSelection(result))
    }

    internal fun buildReferenceString(project: Project, file: VirtualFile, editor: Editor?): String? {
        val baseDir = getProjectBaseDir(project) ?: return null
        val relativePath = VfsUtilCore.getRelativePath(file, baseDir) ?: return null
        return formatReferenceString(relativePath, editor)
    }

    internal fun formatReferenceString(relativePath: String, editor: Editor?): String {
        val sb = StringBuilder("@").append(relativePath)
        val selection = getSelectionLineNumbers(editor)
        if (selection != null) {
            val (startLine, endLine) = selection
            sb.append("#L").append(startLine)
            if (endLine != startLine) {
                sb.append("-").append(endLine)
            }
        }
        return sb.toString()
    }

    internal fun getSelectionLineNumbers(editor: Editor?): Pair<Int, Int>? {
        if (editor == null) return null
        val selectionModel = editor.selectionModel
        if (!selectionModel.hasSelection()) return null
        val document = editor.document
        val startLine = document.getLineNumber(selectionModel.selectionStart) + 1
        val adjustedEnd = if (selectionModel.selectionEnd > 0) selectionModel.selectionEnd - 1 else 0
        val endLine = document.getLineNumber(adjustedEnd) + 1
        return startLine to endLine
    }

    private fun getProjectBaseDir(project: Project): VirtualFile? {
        val basePath = project.basePath ?: return null
        return LocalFileSystem.getInstance().findFileByPath(basePath)
    }
}
