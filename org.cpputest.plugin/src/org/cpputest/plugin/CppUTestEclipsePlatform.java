package org.cpputest.plugin;

import org.cpputest.codeGenerator.CUTPlatformAdaptor;
import org.cpputest.codeGenerator.SourceCodeProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.texteditor.AbstractTextEditor;

public class CppUTestEclipsePlatform implements CUTPlatformAdaptor, SourceCodeProvider {
	private static final String PLUGIN_NAME = "CppUTest";
	IWorkbenchWindow workbenchWindow;

	public CppUTestEclipsePlatform(IWorkbenchWindow window) {
		workbenchWindow = window;
	}

	@Override
	public String getSelectedText() {
		ISelectionService ss = workbenchWindow.getSelectionService();
		ISelection selection = ss.getSelection();
		ITextSelection txt = (ITextSelection) selection;
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
		MessageDialog.openInformation(workbenchWindow.getShell(), PLUGIN_NAME,
				string);

	}

	@Override
	public String getFullText() {
		final IEditorPart activeEditor = workbenchWindow.getActivePage()
				.getActiveEditor();
		if (activeEditor == null)
			return null;
		final AbstractTextEditor textEditor = (AbstractTextEditor) activeEditor
				.getAdapter(AbstractTextEditor.class);
		if (textEditor == null)
			return "";
		
		IDocument document = textEditor.getDocumentProvider().getDocument(
				textEditor.getEditorInput());

		return document.get();
	}

	@Override
	public int getCursorPosition() {
		ISelectionService ss = workbenchWindow.getSelectionService();
		ISelection selection = ss.getSelection();
		ITextSelection txt = (ITextSelection) selection;
		return txt.getOffset();
	}

}
