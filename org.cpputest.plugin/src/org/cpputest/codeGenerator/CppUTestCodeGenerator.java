package org.cpputest.codeGenerator;

import org.cpputest.parser.SourceCodeReader;
import org.cpputest.parser.langunit.CppLangFunctionSignature;

public class CppUTestCodeGenerator implements UnitTestCodeGenerator {
	private final SourceCodeReader signatures;
	private final Stubber stubber;

	public CppUTestCodeGenerator(SourceCodeReader reader, Stubber stubber) {
		this.signatures = reader;
		this.stubber = stubber;
	}

	@Override
	public CppCode getEmptyStubOfCode(String sourceCode) {
		CppCode stubCode = new CppCode();
		for(CppLangFunctionSignature sig: signatures.signatures(sourceCode))
			stubCode.append(stubber.getEmptyCStub(sig));
		
		return stubCode;
	}

	@Override
	public CppCode getEmptyStubOfCodeAtPosition(String allCode, int offset) {
		for(CppLangFunctionSignature sig: signatures.signatures(allCode))
			if (sig.isOffsetInclusive(offset))
				return stubber.getEmptyCStub(sig);
		
		return new CppCode();
	}
}
