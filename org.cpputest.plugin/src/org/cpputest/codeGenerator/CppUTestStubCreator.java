package org.cpputest.codeGenerator;

import org.cpputest.parser.SourceCodeReader;
import org.cpputest.parser.langunit.CppLangFunctionSignature;

public class CppUTestStubCreator implements WhoCreateStubFromSourceCode {
	private final SourceCodeReader signatures;

	public CppUTestStubCreator(SourceCodeReader reader) {
		this.signatures = reader;
	}

	@Override
	public CppCode getStubOfCode(String sourceCode, Stubber stubber) {
		CppCode stubCode = new CppCode();
		for(CppLangFunctionSignature sig: signatures.signatures(sourceCode))
			stubCode.append(stubber.getStubOfSignature(sig));
		
		return stubCode;
	}

	@Override
	public CppCode getStubOfCodeAtPosition(String allCode, int offset,
			Stubber stubber) {
		for(CppLangFunctionSignature sig: signatures.signatures(allCode))
			if (sig.isOffsetInclusive(offset))
				return stubber.getStubOfSignature(sig);
		
		return new CppCode();
	}
}
