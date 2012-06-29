package org.cpputest.plugin.actions;

import org.cpputest.plugin.general.CppUTestCodeGeneratorActions;
import org.cpputest.plugin.general.CppUTestEclipseCodeGeneratorActions;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

public class CppUTestAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private CppUTestCodeGeneratorActions cpputestCodeGenerator = null;
	public CppUTestAction() {
	}

	public CppUTestAction(CppUTestCodeGeneratorActions cpputest) {
		cpputestCodeGenerator = cpputest;
	}

	public void run(IAction action) {
		if (action.getId().equals(
				"org.cpputest.plugin.actions.CopyEmptyStubToClipboard"))
			cpputestCodeGenerator.copyEmptyStubOfSelectedCodeToClipboard();
		else
			MessageDialog.openInformation(window.getShell(),
					"CppUTest Eclipse Plugin", "Hello1, Eclipse world");
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

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
		cpputestCodeGenerator = new CppUTestEclipseCodeGeneratorActions(window);
	}
}