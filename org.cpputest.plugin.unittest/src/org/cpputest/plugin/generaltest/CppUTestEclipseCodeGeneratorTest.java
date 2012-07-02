package org.cpputest.plugin.generaltest;

import org.cpputest.plugin.general.CppCode;
import org.cpputest.plugin.general.UnitTestCodeGenerator;
import org.cpputest.plugin.general.CppUTestEclipseCodeGeneratorActions;
import org.cpputest.plugin.general.CppUTestPlatform;
import org.cpputest.plugin.general.CppCodeFormater;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestEclipseCodeGeneratorTest {
	Mockery context = new JUnit4Mockery();
	@Test
	public void testCopyEmptyStubOfSelectedCodeToClipboard() {
		final CppUTestPlatform platform = context.mock(CppUTestPlatform.class);
		final UnitTestCodeGenerator codeGenerator = context.mock(UnitTestCodeGenerator.class);
		final CppCodeFormater formater = context.mock(CppCodeFormater.class);
		final CppCode code = new CppCode();
		context.checking(new Expectations() {{
	        allowing(platform).getSelectedText();
	        will(returnValue("abc"));
	        oneOf(codeGenerator).getEmptyStubOfCode("abc");
	        will(returnValue(code));
	        oneOf(formater).format(code); will(returnValue("def"));
	        oneOf(platform).copyToClipboard("def");
	    }});		
		CppUTestEclipseCodeGeneratorActions cpputest = new CppUTestEclipseCodeGeneratorActions(platform, codeGenerator, formater);
		cpputest.copyEmptyStubOfSelectedCodeToClipboard();
	}
}
