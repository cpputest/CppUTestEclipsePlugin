package org.cpputest.plugin.SWTBotTest;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class DefaultMockTest extends CppProjectTestBase {
	private static final String GENERAL_PROJECT_FOR_TESTING = "GeneralProjectForTesting";
	@Before
	public void setupProjectAndClearClipboard() throws CoreException {
		createCppProject(GENERAL_PROJECT_FOR_TESTING);
		clearClipboard();
	}
	@After
	public void cleanProject() throws CoreException{
		deleteProject(GENERAL_PROJECT_FOR_TESTING);
	}

	@Test
	public void generateDefaultMockForVoidFunctionWithNoParameter() throws CoreException {
		copyDefaultMockToClipboard("void fun()");
		assertEquals("void fun(){mock().actualCall(\"fun\");}\n", getClipboardContent());
	}

	@Ignore
	@Test
	public void generateDefaultMockForVoidFunctionWithVoidParameter() throws CoreException {
		copyDefaultMockToClipboard("void fun(void)");
		assertEquals("void fun(void){mock().actualCall(\"fun\");}\n", getClipboardContent());
	}

	protected void copyDefaultMockToClipboard(String signature) throws CoreException {
		SWTBotEclipseEditor editor = createNewCppFile(GENERAL_PROJECT_FOR_TESTING, "example.h", signature);
		editor.selectRange(0,0,1000);
		fireTheCopyDefaultMockToClipboardMenuItem();
	}
	
	private void fireTheCopyDefaultMockToClipboardMenuItem() {
		bot.menu("CppUTest").menu("Copy Default Mock To Clipboard").click();
	}

}
