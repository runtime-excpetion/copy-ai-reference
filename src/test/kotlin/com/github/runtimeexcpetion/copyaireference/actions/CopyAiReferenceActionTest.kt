package com.github.runtimeexcpetion.copyaireference.actions

import com.intellij.testFramework.TestDataPath
import com.intellij.testFramework.fixtures.BasePlatformTestCase

@TestDataPath("\$CONTENT_ROOT/src/test/testData")
class CopyAiReferenceActionTest : BasePlatformTestCase() {

    private lateinit var action: CopyAiReferenceAction

    override fun setUp() {
        super.setUp()
        action = CopyAiReferenceAction()
    }

    // === formatReferenceString tests (pure logic, no project needed) ===

    fun testFormatPathOnly() {
        val result = action.formatReferenceString("src/main/kotlin/Test.kt", null)
        assertEquals("@src/main/kotlin/Test.kt", result)
    }

    fun testFormatPathWithoutSelectionDoesNotAddLineNumbers() {
        val result = action.formatReferenceString("src/main/kotlin/Test.kt", null)
        assertFalse(result.contains("#L"))
    }

    fun testFormatPathWithAtsymbol() {
        val result = action.formatReferenceString("some/path/File.kt", null)
        assertTrue(result.startsWith("@"))
    }

    // === getSelectionLineNumbers tests (needs Editor from test fixture) ===

    fun testMultiLineSelectionReturnsCorrectRange() {
        myFixture.configureByText(
            "Test.kt",
            """
            line1
            line2
            line3
            line4
            line5
            """.trimIndent()
        )

        val editor = myFixture.editor
        val document = editor.document
        editor.selectionModel.setSelection(
            document.getLineStartOffset(1),
            document.getLineEndOffset(2)
        )

        val result = action.getSelectionLineNumbers(editor)
        assertNotNull(result)
        assertEquals(2, result?.first) // startLine = 2 (1-based)
        assertEquals(3, result?.second) // endLine = 3 (1-based)
    }

    fun testSingleLineSelectionReturnsSameStartEnd() {
        myFixture.configureByText(
            "Test.kt",
            """
            line1
            line2
            line3
            """.trimIndent()
        )

        val editor = myFixture.editor
        val document = editor.document
        editor.selectionModel.setSelection(
            document.getLineStartOffset(1),
            document.getLineEndOffset(1)
        )

        val result = action.getSelectionLineNumbers(editor)
        assertNotNull(result)
        assertEquals(2, result?.first)
        assertEquals(2, result?.second)
    }

    fun testSelectionOnFirstLineReturnsLine1() {
        myFixture.configureByText(
            "Test.kt",
            "first line\nsecond line\nthird line"
        )

        val editor = myFixture.editor
        val document = editor.document
        editor.selectionModel.setSelection(
            document.getLineStartOffset(0),
            document.getLineEndOffset(1)
        )

        val result = action.getSelectionLineNumbers(editor)
        assertNotNull(result)
        assertEquals(1, result?.first)
        assertEquals(2, result?.second)
    }

    fun testNullEditorReturnsNull() {
        val result = action.getSelectionLineNumbers(null)
        assertNull(result)
    }

    fun testEditorWithoutSelectionReturnsNull() {
        myFixture.configureByText("Test.kt", "some content")

        val result = action.getSelectionLineNumbers(myFixture.editor)
        assertNull(result)
    }

    // === formatReferenceString + getSelectionLineNumbers integration ===

    fun testFormatWithMultiLineSelection() {
        myFixture.configureByText(
            "Test.kt",
            """
            line1
            line2
            line3
            line4
            """.trimIndent()
        )

        val editor = myFixture.editor
        val document = editor.document
        editor.selectionModel.setSelection(
            document.getLineStartOffset(1),
            document.getLineEndOffset(2)
        )

        val result = action.formatReferenceString("src/Test.kt", editor)
        assertEquals("@src/Test.kt#L2-3", result)
    }

    fun testFormatWithSingleLineSelection() {
        myFixture.configureByText(
            "Test.kt",
            """
            line1
            line2
            line3
            """.trimIndent()
        )

        val editor = myFixture.editor
        val document = editor.document
        editor.selectionModel.setSelection(
            document.getLineStartOffset(1),
            document.getLineEndOffset(1)
        )

        val result = action.formatReferenceString("src/Test.kt", editor)
        assertEquals("@src/Test.kt#L2", result)
    }

    fun testFormatWithoutEditorSelection() {
        myFixture.configureByText("Test.kt", "some content")
        val result = action.formatReferenceString("src/Test.kt", myFixture.editor)
        assertEquals("@src/Test.kt", result)
    }
}
