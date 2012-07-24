package org.cpputest.codeGenerator;

import org.cpputest.parser.langunit.CppLangFunctionSignature;

public interface Stubber {

	CppCode getStubOfSignature(CppLangFunctionSignature signature);

}
