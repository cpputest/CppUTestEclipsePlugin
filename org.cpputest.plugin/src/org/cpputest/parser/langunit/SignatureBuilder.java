package org.cpputest.parser.langunit;

public class SignatureBuilder {
	CppLangFunctionSignature signature;
	private String type = "";
	private boolean hasFunctionName = false;
	
	public SignatureBuilder() {
		signature = new CppLangFunctionSignature("");
	}
	public CppLangFunctionSignature build() {
		return signature;
	}
	public SignatureBuilder withReturnType(String type) {
		this.type  = type;
		signature.setReturnType(type);
		return this;
	}
	public SignatureBuilder addToFunctionName(String functionName) {
		hasFunctionName = true;
		signature.setFunctionName(functionName);
		return this;
	}
	public SignatureBuilder addToParameter(String parameter) {
		signature.addToParameter(parameter);
		return this;
	}
	public SignatureBuilder addToReturnType(String string) {
		type += string;
		signature.setReturnType(type);
		return this;
	}
	public boolean isComplete() {
		return hasFunctionName ;
	}
}