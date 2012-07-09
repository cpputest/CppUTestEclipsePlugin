package org.cpputest.plugin.test;

import org.cpputest.plugin.ActionRunner;
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
	final ActionRunner cpputest = context.mock(ActionRunner.class);
	@Test
	public void testGenerateEmptyStub() {
		context.checking(new Expectations() {{
	        oneOf(cpputest).run(actionItem);
	    }});		
		CppUTestAction action = new CppUTestAction(cpputest);
		action.run(actionItem);
	}
}

