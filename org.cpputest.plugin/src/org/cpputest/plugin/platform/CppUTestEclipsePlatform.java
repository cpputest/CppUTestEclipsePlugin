package org.cpputest.plugin.platform;

import org.cpputest.plugin.general.CppUTestPlatform;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;

public class CppUTestEclipsePlatform implements CppUTestPlatform {
	IWorkbenchWindow workbenchWindow;
	public CppUTestEclipsePlatform(IWorkbenchWindow window) {
		workbenchWindow = window;
	}

	@Override
	public String getSelectedText() {
		ISelectionService ss = workbenchWindow.getSelectionService();
		ISelection selection = ss.getSelection();
		return selection.toString();
	}

	@Override
	public void copyToClipboard(String string) {
		Clipboard clipboard = new Clipboard(Display.getDefault());
		clipboard.setContents(new Object[] { string },
				new Transfer[] { TextTransfer.getInstance() });
		clipboard.dispose();
	}

}
