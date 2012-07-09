package org.cpputest.action.test;

import org.cpputest.plugin.Actions;
import org.cpputest.plugin.CppUTestActionRunner;
import org.eclipse.jface.action.IAction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestActionRunnerTest {
	Mockery context = new JUnit4Mockery();
	final Actions actions = context.mock(Actions.class); 
	final IAction action = context.mock(IAction.class);

	@Test
	public void testCopyEmptyStubToClipboard() {
		context.checking(new Expectations() {{
	        allowing(action).getId();
	        will(returnValue("org.cpputest.plugin.actions.CopyEmptyStubToClipboard"));
	        oneOf(actions).copyEmptyStubOfSelectedCodeToClipboard();
	    }});		
		
		CppUTestActionRunner runner = new CppUTestActionRunner(actions);
		runner.run(action);
	}

}
