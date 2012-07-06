package org.cpputest.parser.langunit;

import org.cpputest.parser.CppCode;

public class CppLangFunctionSignature implements LanguageUnit {

	private final String currentFun;
	private String functionName;
	private String returnType;

	public CppLangFunctionSignature(String currentFun) {
		this.currentFun = currentFun;
	}

	@Override
	public String toString() {
		return currentFun;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
		
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
		
	}

	@Override
	public CppCode getCode() {
		// TODO Auto-generated method stub
		return new CppCode(returnType + " " + functionName + "()");
	}

	public boolean isVoid() {
		return returnType.equals("void");
	}
	

}
