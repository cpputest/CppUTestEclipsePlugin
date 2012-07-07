package org.cpputest.plugin.actions;

import org.cpputest.codeGenerator.CompactCppCodeFormater;
import org.cpputest.codeGenerator.CppStubber;
import org.cpputest.codeGenerator.CppUTestCodeGenerator;
import org.cpputest.codeGenerator.Actions;
import org.cpputest.codeGenerator.CppUTestActions;
import org.cpputest.parser.CppSourceCodeReader;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

public class CppUTestAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;
	private Actions cpputestCodeGeneratorActions = null;
	public CppUTestAction() {
	}

	/* constructor for unit testing */
	public CppUTestAction(Actions cpputest) {
		cpputestCodeGeneratorActions = cpputest;
	}

	public void run(IAction action) {
		if (action.getId().equals(
				"org.cpputest.plugin.actions.CopyEmptyStubToClipboard"))
			cpputestCodeGeneratorActions.copyEmptyStubOfSelectedCodeToClipboard();
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
	 * this code has no unittest, perhaps need a factory.
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
		CppSourceCodeReader reader = new CppSourceCodeReader();
		CppStubber stubber = new CppStubber();
		CppUTestEclipsePlatform platform = new CppUTestEclipsePlatform(window);
		CppUTestCodeGenerator codeGenerator = new CppUTestCodeGenerator(reader, stubber);
		CompactCppCodeFormater formater = new CompactCppCodeFormater();
		cpputestCodeGeneratorActions = new CppUTestActions(platform, codeGenerator, formater);
	}
}