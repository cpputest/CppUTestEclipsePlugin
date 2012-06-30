package org.cpputest.plugin.general;

public class CppUTestCodeGeneratorImpl implements CppUTestCodeGenerator {
	CppCodeParser parser = new CppCodeParser();

	@Override
	public String getEmptyCStubOfCode(String string) {
		CppFunction fun = parser.parseFunction(string);
		return fun.getEmptyStub(string);
	}
}
