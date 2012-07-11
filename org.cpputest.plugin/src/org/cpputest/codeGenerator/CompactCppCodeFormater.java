package org.cpputest.codeGenerator;



public class CompactCppCodeFormater implements CppCodeFormater {

	public String format(CppCode code) {
		return code.toString();
	}
}
