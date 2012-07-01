package org.cpputest.plugin.SWTBotTest;

import static org.junit.Assert.*;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEclipseEditor;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
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
	@AfterClass
	static public void waitForAWhile(){
		SWTWorkbenchBot bot = new SWTWorkbenchBot();
		bot.sleep(2000);
	}
	@Test
	public void testCopyEmptyStubToClipboard() {
		String clipboardContent = copyEmptyStubOfCodeToClipboard("void fun();\n");
		assertEquals("void fun(){}\n", clipboardContent);
	}
	@Test
	public void testCopyEmptyStubToClipboardWithReturnType() {
		String clipboardContent = copyEmptyStubOfCodeToClipboard("int fun(void);\n");
		assertEquals("int fun(void){return 0;}\n", clipboardContent);
	}
	@Ignore("still under development")
	@Test
	public void testCopyEmptyStubToClipboardWithIncompleteCode() {
		copyEmptyStubOfCodeToClipboard("  ");
		shouldSeeUnableToGenerateStubMessagebox("No function is selected.");
	}
	@Ignore("still under development")
	public void testCopyEmptyStubCanIgnoreCComment() {
		String clipboardContent = copyEmptyStubOfCodeToClipboard("/* /* */ void /*\"*/fun(void/*\"*/)/**/;/**/\n");
		assertEquals("void fun(void){return 0;}\n", clipboardContent);
	}
	@Ignore("still under development")
	public void testCopyEmptyStubCanIgnoreCppComment() {
		String clipboardContent = copyEmptyStubOfCodeToClipboard("// /* \n void //\"\nfun(void//xxx\n)//\n;//\n");
		assertEquals("void fun(void){return 0;}\n", clipboardContent);
	}
	
	private void shouldSeeUnableToGenerateStubMessagebox(String string) {
		// TODO Auto-generated method stub
		
	}
	protected String copyEmptyStubOfCodeToClipboard(String signature) {
		ICppCodeFormater formater = new CompactCppCodeFormater();
		SWTBotEclipseEditor editor = createNewCppFile("example.h", signature);
		editor.selectLine(0);
		bot.menu("CppUTest").menu("Copy Empty Stub To Clipboard").click();		
		String clipboardContent = getClipboardContent();
		return formater.format(clipboardContent);
	}
}