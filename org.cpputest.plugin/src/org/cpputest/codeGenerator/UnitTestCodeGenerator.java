package org.cpputest.codeGenerator;

import org.cpputest.parser.CppCode;

public interface UnitTestCodeGenerator {
	CppCode getEmptyStubOfCode(String string);
}
