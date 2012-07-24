package org.cpputest.plugin;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppUTestPlatform;
import org.cpputest.codeGenerator.Stubber;

public class CppUTestStubCodeUI implements StubCodeUI {
	public final CppUTestPlatform platform;
	public SourceCodeStubberForEditor sourceCodeStubber;
	public final CppCodeFormater formater;

	public CppUTestStubCodeUI(CppUTestPlatform platform, SourceCodeStubberForEditor sourceCodeStubber, CppCodeFormater formater) {
		this.platform = platform;
		this.sourceCodeStubber = sourceCodeStubber;
		this.formater = formater;
	}
	protected void copyFormatedCodeToClipboard(CppCode stubCode) {
		if (!stubCode.isEmpty()) { 
			String stub = formater.format(stubCode);
			platform.copyToClipboard(stub);
		}
		else
			platform.messageBox("No function is selected.");
	}
	@Override
	public void copyStubCodeInEditorToClipboard(Stubber stubber) {
		CppCode stubCode = sourceCodeStubber.getStubOfCodeInEditor(stubber);
		copyFormatedCodeToClipboard(stubCode);
	}
}