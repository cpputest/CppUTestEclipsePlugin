package org.cpputest.plugin.general;

import java.util.List;

public interface ICppCodeParser {

	List<CppFunction> parseFunctions(String string);

}
