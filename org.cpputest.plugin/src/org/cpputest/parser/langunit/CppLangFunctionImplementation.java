package org.cpputest.parser.langunit;

import org.cpputest.codeGenerator.CppCode;

public class CppLangFunctionImplementation implements LanguageUnit {

	public CppLangFunctionImplementation(String code) {
	}

	@Override
	public CppCode getCode() {
		return null;
	}

	@Override
	public boolean isOffsetInclusive(int offset) {
		return false;
	}

}
