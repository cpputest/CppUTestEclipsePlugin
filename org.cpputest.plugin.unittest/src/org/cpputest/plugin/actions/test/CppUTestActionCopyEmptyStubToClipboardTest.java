package org.cpputest.plugin.actions.test;

import org.cpputest.codeGenerator.Stubber;
import org.cpputest.plugin.ICppUTestFactory;
import org.cpputest.plugin.StubCodeUI;
import org.cpputest.plugin.actions.CppUTestActionCopyDefaultMockToClipboard;
import org.cpputest.plugin.actions.CppUTestActionCopyEmptyStubToClipboard;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestActionCopyEmptyStubToClipboardTest {

	Mockery context = new JUnit4Mockery();
	final Stubber stubber = context.mock(Stubber.class);
	final StubCodeUI stubCodeUI = context.mock(StubCodeUI.class);
	final ICppUTestFactory factory = context.mock(ICppUTestFactory.class);
	CppUTestActionCopyDefaultMockToClipboard action = new CppUTestActionCopyDefaultMockToClipboard(factory);
	
	@Test
	public void testCopyEmptyStubOfSelectedCodeToClipboard() {
		context.checking(new Expectations() {{
			allowing(factory).createDefaultMockStubber(); will(returnValue(stubber));
			allowing(factory).createStubCodeUI(); will(returnValue(stubCodeUI));
	        oneOf(stubCodeUI).copyStubCodeInEditorToClipboard(stubber);;
	    }});		

		action.run(null);
	}

}
