package org.cpputest.parser.langunit;

public class SignatureBuilder {
	CppLangFunctionSignature signature;
	
	public SignatureBuilder() {
		signature = new CppLangFunctionSignature("");
	}
	public SignatureBuilder withReturnType(String type) {
		signature.setReturnType(type);
		return this;
	}
	public SignatureBuilder addToFunctionName(String functionName) {
		signature.setFunctionName(functionName);
		return this;
	}
	public CppLangFunctionSignature build() {
		return signature;
	}
}