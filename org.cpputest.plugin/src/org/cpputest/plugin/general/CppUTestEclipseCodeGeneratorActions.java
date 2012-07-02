package org.cpputest.plugin.general;

import org.cpputest.plugin.platform.CppUTestEclipsePlatform;
import org.eclipse.ui.IWorkbenchWindow;

public class CppUTestEclipseCodeGeneratorActions implements CppUTestCodeGeneratorActions {
	private CppUTestPlatform platform;
	private UnitTestCodeGenerator codeGenerator;
	private CppCodeFormater formater;
	public CppUTestEclipseCodeGeneratorActions(IWorkbenchWindow window) {
		platform = new CppUTestEclipsePlatform(window);
		codeGenerator = new CppUTestCodeGenerator();
		formater = new CompactCppCodeFormater();
	}
	public CppUTestEclipseCodeGeneratorActions(CppUTestPlatform platform,
			UnitTestCodeGenerator codeGenerator, CppCodeFormater formater) {
		this.platform = platform;
		this.codeGenerator = codeGenerator;
		this.formater = formater;
	}
	@Override
	public void copyEmptyStubOfSelectedCodeToClipboard() {
		String code = platform.getSelectedText();
		CppCode stubCode = codeGenerator.getEmptyStubOfCode(code);
		String stub = formater.format(stubCode);
		platform.copyToClipboard(stub);
	}

}
