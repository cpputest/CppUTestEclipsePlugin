package org.cpputest.plugin.general;

import org.cpputest.plugin.platform.CppUTestEclipsePlatform;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;

public class CppUTestEclipseCodeGeneratorActions implements CppUTestCodeGeneratorActions {
	private CppUTestPlatform platform;
	private CppUTestCodeGenerator codeGenerator;
	public CppUTestEclipseCodeGeneratorActions(IWorkbenchWindow window) {
		platform = new CppUTestEclipsePlatform(window);
		codeGenerator = new CppUTestCodeGeneratorImpl();
	}
	public CppUTestEclipseCodeGeneratorActions(CppUTestPlatform platform,
			CppUTestCodeGenerator codeGenerator) {
		this.platform = platform;
		this.codeGenerator = codeGenerator;
	}
	@Override
	public void copyEmptyStubOfSelectedCodeToClipboard() {
		String code = platform.getSelectedText();
		String stub = codeGenerator.getEmptyStubOfCode(code);
		platform.copyToClipboard(stub);
	}

}
