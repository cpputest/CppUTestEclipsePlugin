package org.cpputest.plugin.general;

public class CompactCppCodeFormater implements CppCodeFormater {

	public String format(CppCode code) {
		return code.toString();
	}
}
