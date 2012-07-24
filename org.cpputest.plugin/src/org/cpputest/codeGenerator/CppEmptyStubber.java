package org.cpputest.codeGenerator;

import org.cpputest.parser.langunit.CppLangFunctionSignature;

public class CppEmptyStubber implements Stubber {

	@Override
	public CppCode getStubOfSignature(CppLangFunctionSignature signature) {
		CppCode stub = signature.getCode();
		if (signature.isVoid())
			stub.append("{}\n");
		else
			stub.append("{return 0;}\n");
		return stub;
	}

}
