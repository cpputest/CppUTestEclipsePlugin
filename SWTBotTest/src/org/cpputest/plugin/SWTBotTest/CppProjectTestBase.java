package org.cpputest.plugin.SWTBotTest;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;

public class CppProjectTestBase {
	SWTWorkbenchBot bot = new SWTWorkbenchBot();

	public CppProjectTestBase() {
		super();
	}

	protected void createCppProject(String ProjectName) {
		bot.menu("File").menu("New").menu("Project...").click();
		SWTBotShell shell = bot.shell("New Project");
		shell.activate();
		bot.tree().expandNode("C/C++").select("C++ Project");
		bot.button("Next >").click();
	
		bot.textWithLabel("Project name:").setText(ProjectName);
		bot.button("Finish").click();
	}

	protected void deleteProject(String projectName) {
		bot.viewByTitle("Project Explorer").bot().tree().select(projectName);
		bot.menu("Edit").menu("Delete").click();
		bot.shell("Delete Resources").activate();
		bot.checkBox().click();
		bot.button("OK").click();
	}

	protected String getClipboardContent() {
		SWTBotEclipseEditor temp_editor = createNewCppFile("temp_for_clipboard.h", "");
		temp_editor.contextMenu("Paste").click();
		String content = temp_editor.getText();
		temp_editor.close();
		return content;
	}

	protected SWTBotEclipseEditor createNewCppFile(String fileName, String content) {
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