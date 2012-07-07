package org.cpputest.plugin.test;

import org.cpputest.codeGenerator.Actions;
import org.cpputest.plugin.actions.*;
import org.eclipse.jface.action.IAction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestActionTest {
	Mockery context = new JUnit4Mockery();
	final IAction actionItem = context.mock(IAction.class);
	final Actions cpputest = context.mock(Actions.class);
	@Test
	public void testGenerateEmptyStub() {
		context.checking(new Expectations() {{
	        allowing(actionItem).getId();
	        will(returnValue("org.cpputest.plugin.actions.CopyEmptyStubToClipboard"));
	        oneOf(cpputest).copyEmptyStubOfSelectedCodeToClipboard();
	    }});		
		CppUTestAction action = new CppUTestAction(cpputest);
		action.run(actionItem);
	}
}

