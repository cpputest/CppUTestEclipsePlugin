package org.cpputest.parser.langunit;

import org.cpputest.codeGenerator.CppCode;

public interface LanguageUnit {

	CppCode getCode();

	boolean isOffsetInclusive(int offset);

}
