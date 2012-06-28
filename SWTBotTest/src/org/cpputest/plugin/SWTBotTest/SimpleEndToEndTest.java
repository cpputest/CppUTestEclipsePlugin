package org.cpputest.plugin.SWTBotTest;

import static org.junit.Assert.*;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class SimpleEndToEndTest {

	private SWTWorkbenchBot bot = new SWTWorkbenchBot();

	@Test
	public void testCppUTestMenuExist() {
		bot.menu("CppUTest");
	}
	@Test
	public void testCopyEmptyStubToClipboard() {
		bot.perspectiveByLabel("C/C++").activate();
		createCppProject();
		SWTBotEclipseEditor editor = createNewCppFile("example.h", "void fun(void);\n");
		editor.selectLine(0);
		bot.menu("CppUTest").menu("Copy Empty Stub To Clipboard").click();		
		assertEquals("void fun(){}\n", getClipboardContent());
	
	}
	private void createCppProject() {
		bot.menu("File").menu("New").menu("Project...").click();
		SWTBotShell shell = bot.shell("New Project");
		shell.activate();
		bot.tree().expandNode("C/C++").select("C++ Project");
		bot.button("Next >").click();
 
		bot.textWithLabel("Project name:").setText("GeneralProjectForTesting");
		bot.button("Finish").click();
	}
	private String getClipboardContent() {
		SWTBotEclipseEditor temp_editor = createNewCppFile("temp_for_clipboard.h", "");
		temp_editor.contextMenu("Paste").click();
		String content = temp_editor.getText();
		temp_editor.close();
		return content;
	}
	private SWTBotEclipseEditor createNewCppFile(String fileName, String content) {
		bot.menu("File").menu("New").menu("Other...").click();
		SWTBotShell shell;
		shell = bot.shell("New");
		shell.activate();
		bot.tree().expandNode("C/C++").select("Header File");
		bot.button("Next >").click();
		bot.textWithLabel("Header file:").setText(fileName);
		bot.button("Finish").click();
		
		SWTBotEclipseEditor editor = bot.editorByTitle(fileName).toTextEditor();
		editor.selectRange(0, 0, 1000);
		editor.typeText(content);
		editor.save();
		return editor;
	}


}