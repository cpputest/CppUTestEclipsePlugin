package org.cpputest.plugin.actions;

import org.cpputest.plugin.ICppUTestFactory;
import org.eclipse.jface.action.IAction;

public class CppUTestActionCopyDefaultMockToClipboard extends CppUTestAction {
	public CppUTestActionCopyDefaultMockToClipboard() {
		super();
	}
	public CppUTestActionCopyDefaultMockToClipboard(ICppUTestFactory factory) {
		super(factory);
	}

	public void run(IAction action) {		
		factory.createStubCodeUI().copyStubCodeInEditorToClipboard(
				factory.createDefaultMockStubber());
	}
	
}