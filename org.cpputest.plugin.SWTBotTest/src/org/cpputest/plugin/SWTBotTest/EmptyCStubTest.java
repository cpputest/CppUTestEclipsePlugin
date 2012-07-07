package org.cpputest.plugin.SWTBotTest;

import static org.junit.Assert.*;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotShell;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class EmptyCStubTest extends CppProjectTestBase {

	private static final String GENERAL_PROJECT_FOR_TESTING = "GeneralProjectForTesting";

	@Before
	public void setupProject() {
		bot.perspectiveByLabel("C/C++").activate();
		createCppProject(GENERAL_PROJECT_FOR_TESTING);
	}
	@After
	public void cleanProject(){
		deleteProject(GENERAL_PROJECT_FOR_TESTING);
	}
	@BeforeClass
	static public void increaseTimeOut() {
		SWTBotPreferences.TIMEOUT = 10000;
	}
	@AfterClass
	static public void waitForAWhile(){
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		bot.sleep(2000);
	}
	@Test
	public void testCopyEmptyStubToClipboard() {
		copyEmptyStubOfCodeToClipboard("void fun();\n");
		assertEquals("void fun(){}\n", getClipboardContent());
	}
	@Test
	public void testCopyEmptyStubToClipboardWithReturnType() {
		copyEmptyStubOfCodeToClipboard("int fun(void);\n");
		assertEquals("int fun(void){return 0;}\n", getClipboardContent());
	}
	@Test
	public void testCopyEmptyStubToClipboardWithIncompleteCode() {
		copyEmptyStubOfCodeToClipboard("  ");
		shouldSeeUnableToGenerateStubMessagebox("No function is selected.");
	}
	@Ignore("still under development")
	@Test
	public void testCopyEmptyStubCanIgnoreCComment() {
		copyEmptyStubOfCodeToClipboard("/* /* */ void /*\"*/fun(void/*\"*/)/**/;/**/\n");
		assertEquals("void fun(void){}\n", getClipboardContent());
	}
	@Ignore("still under development")
	@Test
	public void testCopyEmptyStubCanIgnoreCppComment() {
		copyEmptyStubOfCodeToClipboard("// /* \n void //\"\nfun(void//xxx\n)//\n;//\n");
		assertEquals("void fun(void){return 0;}\n", getClipboardContent());
	}

	@Ignore("still under development")
	@Test
	public void testStillAbleToCreateStubWhenSelectedPartOfTheSignature() {
		copyEmptyStubOfCodeToClipboard("// /* \n void //\"\nfun(void//xxx\n)//\n;//\n");
		assertEquals("void fun(void){return 0;}\n", getClipboardContent());
	}

	private void shouldSeeUnableToGenerateStubMessagebox(String string) {
		SWTBotShell shell = bot.shell("CppUTest");
		shell.activate();
		assertTrue(null != bot.label(string));
		bot.button("OK").click();
	
	}
	protected void copyEmptyStubOfCodeToClipboard(String signature) {
		SWTBotEclipseEditor editor = createNewCppFile("example.h", signature);
		editor.selectLine(0);
		bot.menu("CppUTest").menu("Copy Empty Stub To Clipboard").click();		
	}
}