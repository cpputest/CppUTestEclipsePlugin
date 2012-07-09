package org.cpputest.plugin;

import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppUTestPlatform;
import org.cpputest.codeGenerator.UnitTestCodeGenerator;
import org.cpputest.parser.CppCode;

public class CppUTestActions implements Actions {
	public CppUTestPlatform platform;
	public UnitTestCodeGenerator codeGenerator;
	public CppCodeFormater formater;

	public CppUTestActions(CppUTestPlatform platform, UnitTestCodeGenerator codeGenerator, CppCodeFormater formater) {
		this.platform = platform;
		this.codeGenerator = codeGenerator;
		this.formater = formater;
	}
	@Override
	public void copyEmptyStubOfSelectedCodeToClipboard() {
		String code = platform.getSelectedText();
		CppCode stubCode = codeGenerator.getEmptyStubOfCode(code);
		if (!stubCode.isEmpty()) { 
			String stub = formater.format(stubCode);
			platform.copyToClipboard(stub);
		}
		else
			platform.messageBox("No function is selected.");
	}
	
}