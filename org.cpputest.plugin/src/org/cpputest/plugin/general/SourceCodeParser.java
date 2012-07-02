package org.cpputest.plugin.general;

import java.util.List;

public interface SourceCodeParser {

	List<CppFunction> parseFunctions(String string);

}
