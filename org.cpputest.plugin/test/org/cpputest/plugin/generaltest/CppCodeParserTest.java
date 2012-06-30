package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import org.cpputest.plugin.general.CppCodeParser;
import org.cpputest.plugin.general.CppFunction;
import org.junit.Test;

public class CppCodeParserTest {

	private CppCodeParser parser = new CppCodeParser();

	@Test
	public void testParseReferenceReturnType() {
		CppFunction fun = parser.parseFunction("TYPE& fun();");
		assertTrue(fun.isReturnReference());
	}
	@Test
	public void testParseReferenceReturnType2() {
		CppFunction fun = parser.parseFunction("TYPE&fun();");
		assertTrue(fun.isReturnReference());
	}
	@Test
	public void testParseReferenceReturnType3() {
		CppFunction fun = parser.parseFunction("TYPE & fun();");
		assertTrue(fun.isReturnReference());
	}

}
