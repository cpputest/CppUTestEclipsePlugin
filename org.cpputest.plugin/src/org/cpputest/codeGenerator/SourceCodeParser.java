package org.cpputest.codeGenerator;

import java.util.List;

public interface SourceCodeParser {

	List<CppFunction> parseFunctions(String string);

}
