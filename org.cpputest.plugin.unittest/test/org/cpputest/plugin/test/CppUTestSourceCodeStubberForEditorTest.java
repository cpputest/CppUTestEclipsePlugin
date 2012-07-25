package org.cpputest.plugin.test;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.SourceCodeProvider;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.codeGenerator.WhoCreateStubFromSourceCode;
import org.cpputest.plugin.CppUTestSourceCodeStubberForEditor;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class CppUTestSourceCodeStubberForEditorTest {
	private static final int CURSOR_OFFSET = 1;
	Mockery context = new JUnit4Mockery();
	final String EXPECTED_STUB = "stub";
	final String SOURCE_CODE = "code";
	final String ALL_CODE = "all code";
	final SourceCodeProvider resource = context.mock(SourceCodeProvider.class);
	final WhoCreateStubFromSourceCode codeGenerator = context.mock(WhoCreateStubFromSourceCode.class);
	final Stubber stubber = context.mock(Stubber.class);
	@Test
	public void testCopyEmptyStubOfSelectedCodeToClipboard() {
		final CppCode code = new CppCode("not empty");
		context.checking(new Expectations() {{
	        allowing(resource).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getStubOfCode(SOURCE_CODE, stubber);
	        will(returnValue(code));
	    }});		
		CppUTestSourceCodeStubberForEditor cpputest = new CppUTestSourceCodeStubberForEditor(resource, codeGenerator);
		cpputest.getStubOfCodeInEditor(stubber);
	}
	@Test
	public void testShouldTryAgainWithFullTextParsingWhenNoFunctionIsSelected() {
		final CppCode emptyCode = new CppCode();
		final CppCode code = new CppCode("not empty");
		context.checking(new Expectations() {{
	        allowing(resource).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getStubOfCode(SOURCE_CODE, stubber);
	        will(returnValue(emptyCode));
	        allowing(resource).getFullText();
	        will(returnValue(ALL_CODE));
	        allowing(resource).getCursorPosition();
	        will(returnValue(CURSOR_OFFSET));
	        oneOf(codeGenerator).getStubOfCodeAtPosition(ALL_CODE, CURSOR_OFFSET, stubber);
	        will(returnValue(code));
	    }});		
		CppUTestSourceCodeStubberForEditor cpputest = new CppUTestSourceCodeStubberForEditor(resource, codeGenerator);
		assertEquals(code, cpputest.getStubOfCodeInEditor(stubber));
	}
	@Test
	public void testShouldAlertWhenNoFunctionSelect() {
		final CppCode emptyCode = new CppCode();
		context.checking(new Expectations() {{
	        allowing(resource).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getStubOfCode(SOURCE_CODE, stubber);
	        will(returnValue(emptyCode));
	        allowing(resource).getFullText();
	        will(returnValue(ALL_CODE));
	        allowing(resource).getCursorPosition();
	        will(returnValue(CURSOR_OFFSET));
	        oneOf(codeGenerator).getStubOfCodeAtPosition(ALL_CODE, CURSOR_OFFSET, stubber);
	        will(returnValue(emptyCode));
	    }});		
		CppUTestSourceCodeStubberForEditor cpputest = new CppUTestSourceCodeStubberForEditor(resource, codeGenerator);
		assertEquals(emptyCode, cpputest.getStubOfCodeInEditor(stubber));
	}
}
