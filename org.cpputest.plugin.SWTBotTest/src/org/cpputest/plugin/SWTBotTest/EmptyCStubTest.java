package org.cpputest.plugin.SWTBotTest;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class EmptyCStubTest extends CppProjectTestBase {

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
	@BeforeClass
	static public void increaseTimeOut() {
		SWTBotPreferences.TIMEOUT = 20000;
	}
	@AfterClass
	static public void waitForAWhile(){
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		bot.sleep(2000);
	}
	@Test
	public void testCopyEmptyStubToClipboard() throws CoreException {
		copyEmptyStubOfCodeToClipboard("void fun1();\n");
		assertEquals("void fun1(){}\n", getClipboardContent());
	}
	@Test
	public void testCopyEmptyStubToClipboardWithReturnType() throws CoreException {
		copyEmptyStubOfCodeToClipboard("int fun2(void);\n");
		assertEquals("int fun2(void){return 0;}\n", getClipboardContent());
	}
	@Test
	public void testCopyEmptyStubToClipboardWithIncompleteCode() throws CoreException {
		copyEmptyStubOfCodeToClipboard("  ");
		shouldSeeUnableToGenerateStubMessagebox("No function is selected.");
	}
	@Test
	public void testCopyEmptyStubCanIgnoreCComment() throws CoreException {
		copyEmptyStubOfCodeToClipboard("/* /* */ void /*\"*/fun3(void/*\"*/)/**/;/**/\n");
		assertEquals("void fun3(void){}\n", getClipboardContent());
	}
	@Test
	public void testCopyEmptyStubCanIgnoreCppComment() throws CoreException {
		copyEmptyStubOfCodeToClipboard("// /* \n void //\"\nfun4(void//xxx\n)//\n;//\n");
		assertEquals("void fun4(void){}\n", getClipboardContent());
	}

	protected void copyEmptyStubOfCodeToClipboard(String signature) throws CoreException {
		SWTBotEclipseEditor editor = createNewCppFile(GENERAL_PROJECT_FOR_TESTING, "example.h", signature);
		editor.selectRange(0,0,1000);
		fireTheCopyEmptyStubToClipboardMenuItem();
	}
}