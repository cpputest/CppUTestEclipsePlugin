package org.cpputest.plugin.actions;

import org.cpputest.plugin.ActionRunner;
import org.cpputest.plugin.CppUTestFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class CppUTestAction implements IWorkbenchWindowActionDelegate {
	private ActionRunner cpputestCodeGeneratorActions = null;
	public CppUTestAction() {
	}

	/* constructor for unit testing */
	public CppUTestAction(ActionRunner cpputest) {
		cpputestCodeGeneratorActions = cpputest;
	}

	public void run(IAction action) {
		cpputestCodeGeneratorActions.run(action);
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
		cpputestCodeGeneratorActions = CppUTestFactory.createCppUTestCodeGeneratorActions(window);
	}

}