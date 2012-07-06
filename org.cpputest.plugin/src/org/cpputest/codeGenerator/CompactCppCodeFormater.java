package org.cpputest.codeGenerator;

import org.cpputest.parser.CppCode;

public class CompactCppCodeFormater implements CppCodeFormater {

	public String format(CppCode code) {
		return code.toString();
	}
}
