package org.cpputest.plugin;

import org.cpputest.parser.CppCode;
import org.eclipse.jface.action.IAction;

public class CppUTestActionRunner implements ActionRunner {
	private Actions actions;
	public CppUTestActionRunner(Actions actions) {
		this.actions = actions;
	}
	@Override
	public void run(IAction action) {
		if (action.getId().equals(
				"org.cpputest.plugin.actions.CopyEmptyStubToClipboard"))
			actions.copyEmptyStubOfSelectedCodeToClipboard();
	}

}
