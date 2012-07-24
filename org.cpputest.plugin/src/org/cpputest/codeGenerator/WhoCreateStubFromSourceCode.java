package org.cpputest.codeGenerator;

public interface WhoCreateStubFromSourceCode {
	CppCode getStubOfCode(String string, Stubber stubber);

	CppCode getStubOfCodeAtPosition(String aLL_CODE, int oFFSET, Stubber stubber);
}
