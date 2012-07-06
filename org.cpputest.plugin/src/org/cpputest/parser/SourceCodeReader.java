package org.cpputest.parser;

import org.cpputest.parser.langunit.CppLangFunctionSignature;

public interface SourceCodeReader {

	void read(String sourceCode);

	Iterable<CppLangFunctionSignature> signatures(String sourceCode);

}
