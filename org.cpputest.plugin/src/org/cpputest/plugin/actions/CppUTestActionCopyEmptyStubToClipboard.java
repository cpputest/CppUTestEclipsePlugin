package org.cpputest.plugin.actions;

import org.cpputest.plugin.ICppUTestFactory;
import org.eclipse.jface.action.IAction;

public class CppUTestActionCopyEmptyStubToClipboard extends CppUTestAction {
	public CppUTestActionCopyEmptyStubToClipboard() {
		super();
	}
	public CppUTestActionCopyEmptyStubToClipboard(ICppUTestFactory factory) {
		super(factory);
	}

	public void run(IAction action) {		
		factory.createStubCodeUI().copyStubCodeInEditorToClipboard(
				factory.createEmptyStubber());
	}
	
}