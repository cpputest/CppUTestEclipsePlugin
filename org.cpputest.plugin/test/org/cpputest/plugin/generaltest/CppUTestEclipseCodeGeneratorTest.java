package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import org.cpputest.plugin.general.CppUTestCodeGenerator;
import org.cpputest.plugin.general.CppUTestEclipseCodeGeneratorActions;
import org.cpputest.plugin.general.CppUTestPlatform;
import org.eclipse.jface.action.IAction;
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
		final CppUTestCodeGenerator codeGenerator = context.mock(CppUTestCodeGenerator.class);
		context.checking(new Expectations() {{
	        allowing(platform).getSelectedText();
	        will(returnValue("abc"));
	        oneOf(codeGenerator).getEmptyStubOfCode("abc");
	        will(returnValue("def"));
	        oneOf(platform).copyToClipboard("def");
	    }});		
		CppUTestEclipseCodeGeneratorActions cpputest = new CppUTestEclipseCodeGeneratorActions(platform, codeGenerator);
		cpputest.copyEmptyStubOfSelectedCodeToClipboard();
	}

}
