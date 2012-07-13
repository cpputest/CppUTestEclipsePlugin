package org.cpputest.plugin;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.SourceCodeResource;
import org.cpputest.codeGenerator.UnitTestCodeGenerator;

public class CppUTestSourceCodeStubber implements SourceCodeStubber {
	private final SourceCodeResource resource;
	private final UnitTestCodeGenerator codeGenerator;

	public CppUTestSourceCodeStubber(SourceCodeResource resource,
			UnitTestCodeGenerator codeGenerator) {
				this.resource = resource;
				this.codeGenerator = codeGenerator;
	}
	public CppCode getEmptyStubOfCodeInEditor() {
		CppCode stubCode = getStubOfSelectedCode();
		if (stubCode.isEmpty()) { 
			stubCode = getStubOfCodeAtPosition();
		}
		return stubCode;
	}
	protected CppCode getStubOfCodeAtPosition() {
		CppCode stubCode;
		String allCode = resource.getFullText();
		int pos = resource.getCursorPosition();
		stubCode = codeGenerator.getEmptyStubOfCodeAtPosition(allCode, pos);
		return stubCode;
	}
	protected CppCode getStubOfSelectedCode() {
		String code = resource.getSelectedText();
		CppCode stubCode = codeGenerator.getEmptyStubOfCode(code);
		return stubCode;
	}
}