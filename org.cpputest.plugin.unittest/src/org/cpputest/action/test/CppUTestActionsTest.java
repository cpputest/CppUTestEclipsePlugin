package org.cpputest.action.test;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppUTestPlatform;
import org.cpputest.codeGenerator.UnitTestCodeGenerator;
import org.cpputest.plugin.CppUTestActions;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.eclipse.jface.text.Position;


@RunWith(JMock.class)
public class CppUTestActionsTest {
	Mockery context = new JUnit4Mockery();
	final String EXPECTED_STUB = "stub";
	final String SOURCE_CODE = "code";
	final String ALL_CODE = "all code";
	final CppUTestPlatform platform = context.mock(CppUTestPlatform.class);
	final UnitTestCodeGenerator codeGenerator = context.mock(UnitTestCodeGenerator.class);
	final CppCodeFormater formater = context.mock(CppCodeFormater.class);
	@Test
	public void testCopyEmptyStubOfSelectedCodeToClipboard() {
		final CppCode code = new CppCode("not empty");
		context.checking(new Expectations() {{
	        allowing(platform).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getEmptyStubOfCode(SOURCE_CODE);
	        will(returnValue(code));
	        oneOf(formater).format(code); will(returnValue(EXPECTED_STUB));
	        oneOf(platform).copyToClipboard(EXPECTED_STUB);
	    }});		
		CppUTestActions cpputest = new CppUTestActions(platform, codeGenerator, formater);
		cpputest.copyEmptyStubOfSelectedCodeToClipboard();
	}
	@Test
	public void testShouldTryAgainWithFullTextParsingWhenNoFunctionIsSelected() {
		final CppCode emptyCode = new CppCode();
		final CppCode code = new CppCode("not empty");
		final Position pos = new Position(1);
		context.checking(new Expectations() {{
	        allowing(platform).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getEmptyStubOfCode(SOURCE_CODE);
	        will(returnValue(emptyCode));
	        allowing(platform).getFullText();
	        will(returnValue(ALL_CODE));
	        allowing(platform).getCursorPosition();
	        will(returnValue(pos));
	        oneOf(codeGenerator).getEmptyStubOfCodeAtPosition(ALL_CODE, 1);
	        will(returnValue(code));
	        oneOf(formater).format(code); will(returnValue(EXPECTED_STUB));
	        oneOf(platform).copyToClipboard(EXPECTED_STUB);
	    }});		
		CppUTestActions cpputest = new CppUTestActions(platform, codeGenerator, formater);
		cpputest.copyEmptyStubOfSelectedCodeToClipboard();
	}
	@Test
	public void testShouldAlertWhenNoFunctionSelect() {
		final CppCode emptyCode = new CppCode();
		final Position pos = new Position(1);
		context.checking(new Expectations() {{
	        allowing(platform).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getEmptyStubOfCode(SOURCE_CODE);
	        will(returnValue(emptyCode));
	        allowing(platform).getFullText();
	        will(returnValue(ALL_CODE));
	        allowing(platform).getCursorPosition();
	        will(returnValue(pos));
	        oneOf(codeGenerator).getEmptyStubOfCodeAtPosition(ALL_CODE, 1);
	        will(returnValue(emptyCode));
	        oneOf(platform).messageBox("No function is selected.");
	    }});		
		CppUTestActions cpputest = new CppUTestActions(platform, codeGenerator, formater);
		cpputest.copyEmptyStubOfSelectedCodeToClipboard();
	}
}
