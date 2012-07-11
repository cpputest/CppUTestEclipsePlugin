package org.cpputest.codeGenerator;

import org.cpputest.parser.langunit.CppLangFunctionSignature;

public class CppStubber implements Stubber {

	@Override
	public CppCode getEmptyCStub(CppLangFunctionSignature signature) {
		CppCode stub = signature.getCode();
		if (signature.isVoid())
			stub.append("{}\n");
		else
			stub.append("{return 0;}\n");
		return stub;
	}

}
