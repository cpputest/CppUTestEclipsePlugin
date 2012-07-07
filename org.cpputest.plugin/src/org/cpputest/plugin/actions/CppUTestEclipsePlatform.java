package org.cpputest.plugin.actions;

import org.cpputest.codeGenerator.CppUTestPlatform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;

public class CppUTestEclipsePlatform implements CppUTestPlatform {
	private static final String PLUGIN_NAME = "CppUTest";
	IWorkbenchWindow workbenchWindow;
	public CppUTestEclipsePlatform(IWorkbenchWindow window) {
		workbenchWindow = window;
	}

	@Override
	public String getSelectedText() {
		ISelectionService ss = workbenchWindow.getSelectionService();
		ISelection selection = ss.getSelection();
		ITextSelection txt = (ITextSelection)selection;
		if (txt != null)
			return txt.getText();
		return selection.toString();
	}

	@Override
	public void copyToClipboard(String string) {
		Clipboard clipboard = new Clipboard(Display.getDefault());
		clipboard.setContents(new Object[] { string },
				new Transfer[] { TextTransfer.getInstance() });
		clipboard.dispose();
	}

	@Override
	public void messageBox(String string) {
		MessageDialog.openInformation(workbenchWindow.getShell(),
				PLUGIN_NAME, string);
		
	}

}
