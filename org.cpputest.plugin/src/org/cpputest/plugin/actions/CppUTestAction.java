package org.cpputest.plugin.actions;

import org.cpputest.codeGenerator.CUTPlatformAdaptor;
import org.cpputest.plugin.CppUTestFactory;
import org.cpputest.plugin.ICppUTestFactory;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public abstract class CppUTestAction implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {
	protected ICppUTestFactory factory = new CppUTestFactory();
	private CUTPlatformAdaptor platform;
	public CppUTestAction() {
		this.factory = new CppUTestFactory();
	}

	public CppUTestAction(ICppUTestFactory factory) {
		this.factory = factory;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		initializePlatform(targetPart.getSite().getWorkbenchWindow());
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
		initializePlatform(window);
	}

	private void initializePlatform(IWorkbenchWindow window) {
		this.platform = factory.createPlatformAdaptor(window);
	}
	protected CUTPlatformAdaptor getPlatform() {
		return this.platform;
	}

}