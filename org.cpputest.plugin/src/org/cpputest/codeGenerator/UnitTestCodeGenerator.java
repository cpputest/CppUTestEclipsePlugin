package org.cpputest.codeGenerator;

public interface UnitTestCodeGenerator {
	CppCode getEmptyStubOfCode(String string);

	CppCode getEmptyStubOfCodeAtPosition(String aLL_CODE, int oFFSET);
}
