package org.cpputest.codeGenerator;

import org.cpputest.parser.CppCode;

public interface CppCodeFormater {

	public abstract String format(CppCode code);

}