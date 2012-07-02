package org.cpputest.plugin.general;

public class CppCode {
	private StringBuilder code = new StringBuilder();
	public CppCode(){
	}
	public CppCode(String str){
		code.append(str);
	}
	public void append(CppCode emptyStub) {
		code.append(emptyStub.toString());
	}
	@Override
	public String toString() {
		return code.toString();
	}
}
