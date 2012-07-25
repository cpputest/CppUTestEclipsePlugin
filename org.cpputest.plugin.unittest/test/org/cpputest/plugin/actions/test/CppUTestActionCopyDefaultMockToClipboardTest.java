package org.cpputest.plugin.actions.test;

import org.cpputest.codeGenerator.Stubber;
import org.cpputest.plugin.ICppUTestFactory;
import org.cpputest.plugin.StubCodeUI;
import org.cpputest.plugin.actions.CppUTestActionCopyEmptyStubToClipboard;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestActionCopyDefaultMockToClipboardTest {

	Mockery context = new JUnit4Mockery();
	final Stubber emptyStubber = context.mock(Stubber.class);
	final StubCodeUI stubCodeUI = context.mock(StubCodeUI.class);
	final ICppUTestFactory factory = context.mock(ICppUTestFactory.class);
	CppUTestActionCopyEmptyStubToClipboard action = new CppUTestActionCopyEmptyStubToClipboard(factory);
	
	@Test
	public void testCopyEmptyStubOfSelectedCodeToClipboard() {
		context.checking(new Expectations() {{
			allowing(factory).createEmptyStubber(); will(returnValue(emptyStubber));
			allowing(factory).createStubCodeUI(); will(returnValue(stubCodeUI));
	        oneOf(stubCodeUI).copyStubCodeInEditorToClipboard(emptyStubber);;
	    }});		

		action.run(null);
	}

}
