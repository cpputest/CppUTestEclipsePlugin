package org.cpputest.plugin.generaltest;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.SourceCodeResource;
import org.cpputest.codeGenerator.UnitTestCodeGenerator;
import org.cpputest.plugin.CppUTestSourceCodeStubber;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(JMock.class)
public class CppUTestSourceCodeStubberTest {
	private static final int CURSOR_OFFSET = 1;
	Mockery context = new JUnit4Mockery();
	final String EXPECTED_STUB = "stub";
	final String SOURCE_CODE = "code";
	final String ALL_CODE = "all code";
	final SourceCodeResource resource = context.mock(SourceCodeResource.class);
	final UnitTestCodeGenerator codeGenerator = context.mock(UnitTestCodeGenerator.class);
	@Test
	public void testCopyEmptyStubOfSelectedCodeToClipboard() {
		final CppCode code = new CppCode("not empty");
		context.checking(new Expectations() {{
	        allowing(resource).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getEmptyStubOfCode(SOURCE_CODE);
	        will(returnValue(code));
	    }});		
		CppUTestSourceCodeStubber cpputest = new CppUTestSourceCodeStubber(resource, codeGenerator);
		cpputest.getEmptyStubOfCodeInEditor();
	}
	@Test
	public void testShouldTryAgainWithFullTextParsingWhenNoFunctionIsSelected() {
		final CppCode emptyCode = new CppCode();
		final CppCode code = new CppCode("not empty");
		context.checking(new Expectations() {{
	        allowing(resource).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getEmptyStubOfCode(SOURCE_CODE);
	        will(returnValue(emptyCode));
	        allowing(resource).getFullText();
	        will(returnValue(ALL_CODE));
	        allowing(resource).getCursorPosition();
	        will(returnValue(CURSOR_OFFSET));
	        oneOf(codeGenerator).getEmptyStubOfCodeAtPosition(ALL_CODE, CURSOR_OFFSET);
	        will(returnValue(code));
	    }});		
		CppUTestSourceCodeStubber cpputest = new CppUTestSourceCodeStubber(resource, codeGenerator);
		assertEquals(code, cpputest.getEmptyStubOfCodeInEditor());
	}
	@Test
	public void testShouldAlertWhenNoFunctionSelect() {
		final CppCode emptyCode = new CppCode();
		context.checking(new Expectations() {{
	        allowing(resource).getSelectedText();
	        will(returnValue(SOURCE_CODE));
	        oneOf(codeGenerator).getEmptyStubOfCode(SOURCE_CODE);
	        will(returnValue(emptyCode));
	        allowing(resource).getFullText();
	        will(returnValue(ALL_CODE));
	        allowing(resource).getCursorPosition();
	        will(returnValue(CURSOR_OFFSET));
	        oneOf(codeGenerator).getEmptyStubOfCodeAtPosition(ALL_CODE, CURSOR_OFFSET);
	        will(returnValue(emptyCode));
	    }});		
		CppUTestSourceCodeStubber cpputest = new CppUTestSourceCodeStubber(resource, codeGenerator);
		assertEquals(emptyCode, cpputest.getEmptyStubOfCodeInEditor());
	}
}
