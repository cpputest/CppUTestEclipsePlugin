package org.cpputest.codeGenerator;

import org.cpputest.parser.CppCode;
import org.cpputest.parser.langunit.CppLangFunctionSignature;

public interface Stubber {

	CppCode getEmptyCStub(CppLangFunctionSignature s1);

}
