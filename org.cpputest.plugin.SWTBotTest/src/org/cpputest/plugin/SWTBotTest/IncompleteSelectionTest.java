package org.cpputest.plugin.SWTBotTest;

import static org.junit.Assert.*;

import org.eclipse.core.runtime.CoreException;
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
public class IncompleteSelectionTest extends CppProjectTestBase {

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
	public void onlySelectedFunctionsShouldBeUsedWhenSelectCompleteFunctionSignature() throws CoreException {
		SWTBotEclipseEditor editor = openAnEditorWith3Functions();
		editor.selectLine(1);
		fireTheCopyEmptyStubToClipboardMenuItem();		
		assertEquals("void fun2(){}\n", getClipboardContent());
	}
	@Test
	public void theFunctionAfterTheCurserShouldBeUsedWhenPutCurserAtTheBeginingOfTheSignature() throws CoreException {
		SWTBotEclipseEditor editor = openAnEditorWith3Functions();
		editor.selectRange(1, 0, 0);
		fireTheCopyEmptyStubToClipboardMenuItem();		
		assertEquals("void fun2(){}\n", getClipboardContent());
	}
	@Test
	public void theFunctionOnTheCurserShouldBeUsedWhenPutCurserInTheMiddleOfTheSignature() throws CoreException {
		SWTBotEclipseEditor editor = openAnEditorWith3Functions();
		editor.selectRange(1, 7, 0);
		fireTheCopyEmptyStubToClipboardMenuItem();		
		assertEquals("void fun2(){}\n", getClipboardContent());
	}
	@Test
	public void onlyTheCompleteFunctionsInTheSelectionShouldBeUsedWhenSelectMultipleSignature() throws CoreException {
		SWTBotEclipseEditor editor = openAnEditorWith3Functions();
		editor.selectRange(0, 7 //start from the middle of fun1
				, 39 // end at the middle of fun4
				);
		fireTheCopyEmptyStubToClipboardMenuItem();		
		assertEquals("void fun2(){}\nvoid fun3(){}\n", getClipboardContent());
	}

	protected SWTBotEclipseEditor openAnEditorWith3Functions()
			throws CoreException {
		SWTBotEclipseEditor editor = createNewCppFile(GENERAL_PROJECT_FOR_TESTING, "example.h", 
				"void fun1();"+System.getProperty("line.separator")+
				"void fun2();"+System.getProperty("line.separator")+
				"void fun3();"+System.getProperty("line.separator")+
				"void fun4();");
		return editor;
	}
}