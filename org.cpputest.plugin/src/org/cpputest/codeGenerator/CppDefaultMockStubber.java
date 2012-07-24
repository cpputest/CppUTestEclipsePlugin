package org.cpputest.codeGenerator;

import org.cpputest.parser.langunit.CppLangFunctionSignature;

public class CppDefaultMockStubber implements Stubber {

	@Override
	public CppCode getStubOfSignature(CppLangFunctionSignature signature) {
		CppCode stub = signature.getCode();
		stub.append("{");
		stub.append("mock().actualCall(\"");
		stub.append(signature.getFunctionName());
		stub.append("\");");
		stub.append("}\n");
		return stub;
	}

}
