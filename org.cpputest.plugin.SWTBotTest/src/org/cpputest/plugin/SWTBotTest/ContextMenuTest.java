package org.cpputest.plugin.SWTBotTest;

import static org.junit.Assert.*;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;

import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class ContextMenuTest extends CppProjectTestBase {
	SWTBotEclipseEditor editor;
	private static final String GENERAL_PROJECT_FOR_TESTING = "GeneralProjectForTesting";

	@Before
	public void setupProject() throws CoreException {
		
		createCppProject(GENERAL_PROJECT_FOR_TESTING);
		editor = createNewCppFile(GENERAL_PROJECT_FOR_TESTING, "example.h", "");
	}
	@After
	public void cleanProject() throws CoreException{
		deleteProject(GENERAL_PROJECT_FOR_TESTING);
	}
	@BeforeClass
	static public void increaseTimeOut() {
		SWTBotPreferences.TIMEOUT = 20000;
	}
	@AfterClass
	static public void waitForAWhile(){
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		bot.sleep(2000);
	}
	@Ignore("Cannot run due to a bug in SWTBot, where a popup menu added by plugin.xml cannot be found.")
	@Test
	public void testCopyEmptyStubToClipboard() {
		editor.setText("void bar();");
		editor.save();
		editor.selectRange(0,0,1000);
		clickContextMenu(editor, "Copy Empty Stub To Clipboard");
		assertEquals("void bar(){}\n", getClipboardContent());
	}
}