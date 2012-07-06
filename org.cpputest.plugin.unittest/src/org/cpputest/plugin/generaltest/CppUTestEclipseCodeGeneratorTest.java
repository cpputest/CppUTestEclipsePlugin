package org.cpputest.plugin.generaltest;

import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppUTestActions;
import org.cpputest.codeGenerator.CppUTestPlatform;
import org.cpputest.codeGenerator.UnitTestCodeGenerator;
import org.cpputest.parser.CppCode;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestEclipseCodeGeneratorTest {
	Mockery context = new JUnit4Mockery();
	final String EXPECTED_STUB = "stub";
	final String SOURCE_CODE = "code";
	@Test
	public void testCopyEmptyStubOfSelectedCodeToClipboard() {
		final CppUTestPlatform platform = context.mock(CppUTestPlatform.class);
		final UnitTestCodeGenerator codeGenerator = context.mock(UnitTestCodeGenerator.class);
		final CppCodeFormater formater = context.mock(CppCodeFormater.class);
		final CppCode code = new CppCode();
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
}
