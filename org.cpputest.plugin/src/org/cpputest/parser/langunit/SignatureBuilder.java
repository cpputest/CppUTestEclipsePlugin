package org.cpputest.parser.langunit;

public class SignatureBuilder {
	CppLangFunctionSignature signature;
	
	public SignatureBuilder() {
		signature = new CppLangFunctionSignature("");
	}
	public CppLangFunctionSignature build() {
		return signature;
	}
	public SignatureBuilder withReturnType(String type) {
		signature.setReturnType(type);
		return this;
	}
	public SignatureBuilder addToFunctionName(String functionName) {
		signature.setFunctionName(functionName);
		return this;
	}
	public SignatureBuilder addToParameter(String parameter) {
		signature.addToParameter(parameter);
		return this;
	}
}