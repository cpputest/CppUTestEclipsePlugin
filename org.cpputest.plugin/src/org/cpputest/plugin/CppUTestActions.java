package org.cpputest.plugin;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppUTestPlatform;

public class CppUTestActions implements Actions {
	public final CppUTestPlatform platform;
	public SourceCodeStubber sourceCodeStubber;
	public final CppCodeFormater formater;

	public CppUTestActions(CppUTestPlatform platform, SourceCodeStubber sourceCodeStubber, CppCodeFormater formater) {
		this.platform = platform;
		this.sourceCodeStubber = sourceCodeStubber;
		this.formater = formater;
	}
	@Override
	public void copyEmptyStubOfSelectedCodeToClipboard() {
		CppCode stubCode = sourceCodeStubber.getEmptyStubOfCodeInEditor();
		if (!stubCode.isEmpty()) { 
			String stub = formater.format(stubCode);
			platform.copyToClipboard(stub);
		}
		else
			platform.messageBox("No function is selected.");
	}
}