package org.cpputest.parser;

import org.cpputest.parser.langunit.CppLangFunctionSignature;

public interface SourceCodeReader {

	Iterable<CppLangFunctionSignature> signatures(String sourceCode);

}
