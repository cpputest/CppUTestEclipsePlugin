package org.cpputest.plugin.actions.test;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CUTPlatformAdaptor;
import org.cpputest.plugin.ICppUTestFactory;
import org.cpputest.plugin.actions.*;
import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestActionTest {
	Mockery context = new JUnit4Mockery();
	final ICppUTestFactory factory = context.mock(ICppUTestFactory.class);
	final CUTPlatformAdaptor platform = context.mock(CUTPlatformAdaptor.class);
	final IWorkbenchWindow window = context.mock(IWorkbenchWindow.class);

	@Test
	public void testCppUTestActionAbstractClassWillCreatePlatformForFutureUse() {
		context.checking(new Expectations() {{
	        oneOf(factory).createPlatformAdaptor(window); will(returnValue(platform));
	    }});		

		IWorkbenchWindowActionDelegate action = new CppUTestAction(factory){
			@Override
			public void run(IAction arg0) {
				assertEquals(platform, this.getPlatform());
			}

		};
		action.init(window);
		action.run(null);
	}

	@Test
	public void testCppUTestActionAbstractClassWillCreatePlatformForObjectActionToo() {
		final IWorkbenchPart targetPart = context.mock(IWorkbenchPart.class);
		final IWorkbenchPartSite site = context.mock(IWorkbenchPartSite.class);

		context.checking(new Expectations() {{
			allowing(targetPart).getSite();will(returnValue(site));
			allowing(site).getWorkbenchWindow();will(returnValue(window));
	        oneOf(factory).createPlatformAdaptor(window); will(returnValue(platform));
	    }});		

		IObjectActionDelegate action = new CppUTestAction(factory){
			@Override
			public void run(IAction arg0) {
				assertEquals(platform, this.getPlatform());
			}
		};
		action.setActivePart(null, targetPart );
		action.run(null);
	}
}

