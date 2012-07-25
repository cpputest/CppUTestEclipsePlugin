package org.cpputest.plugin.test;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CUTPlatformAdaptor;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.plugin.CppUTestStubCodeUI;
import org.cpputest.plugin.SourceCodeStubberForEditor;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JMock.class)
public class CppUTestStubCodeUITest {
	Mockery context = new JUnit4Mockery();
	final String EXPECTED_STUB = "stub";
	final String SOURCE_CODE = "code";
	final String ALL_CODE = "all code";
	final CUTPlatformAdaptor platform = context.mock(CUTPlatformAdaptor.class);
	final SourceCodeStubberForEditor stubbers = context.mock(SourceCodeStubberForEditor.class);
	final CppCodeFormater formater = context.mock(CppCodeFormater.class);
	final Stubber stubber = context.mock(Stubber.class);
	CppUTestStubCodeUI cpputest = new CppUTestStubCodeUI(platform, stubbers, formater);
	@Test
	public void testCopyStubOfSelectedCodeToClipboard() {
		final CppCode code = new CppCode("not empty");
		context.checking(new Expectations() {{
	        allowing(stubbers).getStubOfCodeInEditor(stubber); will(returnValue(code));
	        oneOf(formater).format(code); will(returnValue(EXPECTED_STUB));
	        oneOf(platform).copyToClipboard(EXPECTED_STUB);
	    }});		
		cpputest.copyStubCodeInEditorToClipboard(stubber);
	}
	
	@Test
	public void testShouldAlertWhenNoFunctionSelect() {
		final CppCode emptyCode = new CppCode();
		context.checking(new Expectations() {{
	        oneOf(stubbers).getStubOfCodeInEditor(stubber); will(returnValue(emptyCode));
	        oneOf(platform).messageBox("No function is selected.");
	    }});		
		cpputest.copyStubCodeInEditorToClipboard(stubber);
	}
}
