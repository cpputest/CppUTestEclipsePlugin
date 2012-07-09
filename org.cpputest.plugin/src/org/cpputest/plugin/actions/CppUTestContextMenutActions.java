package org.cpputest.plugin.actions;

import org.cpputest.plugin.CppUTestActionRunner;
import org.cpputest.plugin.CppUTestFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public class CppUTestContextMenutActions implements IObjectActionDelegate {

	private CppUTestActionRunner cpputestCodeGeneratorActions;

	public CppUTestContextMenutActions() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		cpputestCodeGeneratorActions = CppUTestFactory.createCppUTestCodeGeneratorActions(
				targetPart.getSite().getWorkbenchWindow());
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		cpputestCodeGeneratorActions.run(action);
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
