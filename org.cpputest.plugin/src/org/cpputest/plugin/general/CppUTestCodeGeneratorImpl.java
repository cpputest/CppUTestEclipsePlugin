package org.cpputest.plugin.general;

import java.util.List;

public class CppUTestCodeGeneratorImpl implements CppUTestCodeGenerator {
	private ICppCodeParser parser = new CppCodeParser();

	public CppUTestCodeGeneratorImpl() {
	}

	@Override
	public String getEmptyCStubOfCode(String string) {
		List<CppFunction> funs = parser.parseFunctions(string);
		StringBuilder allstubs = new StringBuilder();
		for(CppFunction fun: funs)
			allstubs.append(fun.getEmptyStub());
		return allstubs.toString();
	}
}
