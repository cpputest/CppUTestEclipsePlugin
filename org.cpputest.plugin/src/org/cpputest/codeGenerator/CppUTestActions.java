package org.cpputest.codeGenerator;

import org.cpputest.parser.CppCode;

public class CppUTestActions implements Actions {
	private CppUTestPlatform platform;
	private UnitTestCodeGenerator codeGenerator;
	private CppCodeFormater formater;
	public CppUTestActions(CppUTestPlatform platform,
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
