package org.cpputest.plugin.general;

public class CppUTestCodeGeneratorImpl implements CppUTestCodeGenerator {

	@Override
	public String getEmptyStubOfCode(String string) {
		return "void fun(){}";
	}

}
