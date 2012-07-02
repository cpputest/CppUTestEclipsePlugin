package org.cpputest.plugin.general;

import java.util.List;

public class CppUTestCodeGenerator implements UnitTestCodeGenerator {
	private SourceCodeParser parser = new CppCodeParser();

	public CppUTestCodeGenerator() {
	}

	@Override
	public CppCode getEmptyStubOfCode(String string) {
		List<CppFunction> funs = parser.parseFunctions(string);
		CppCode allstubs = new CppCode();
		for(CppFunction fun: funs)
			allstubs.append(fun.getEmptyStub());
		return allstubs;
	}
}
