package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import java.util.List;

import org.cpputest.plugin.general.CppCodeParser;
import org.cpputest.plugin.general.CppFunction;
import org.junit.Test;

public class CppCodeParserTest {

	private CppCodeParser parser = new CppCodeParser();

	@Test
	public void testParseReferenceReturnType() {
		CppFunction fun = parseSingleFunction("TYPE& fun();");
		assertTrue(fun.isReturnReference());
	}
	@Test
	public void testParseReferenceReturnType2() {
		CppFunction fun = parseSingleFunction("TYPE&fun();");
		assertTrue(fun.isReturnReference());
	}
	@Test
	public void testParseReferenceReturnType3() {
		CppFunction fun = parseSingleFunction("TYPE & fun();");
		assertTrue(fun.isReturnReference());
	}
	@Test
	public void testParseWithMoreSpaces() {
		List<CppFunction> funs = parser.parseFunctions(" \n TYPE fun() \n ; \n ");
		assertEquals(1, funs.size());
	}
	@Test
	public void testParseTwoFunctions() {
		List<CppFunction> funs = parser.parseFunctions("TYPE fun();void bar();");
		assertEquals(2, funs.size());
		assertEquals("void bar()", funs.get(1).getSignature());
		assertEquals("void", funs.get(1).getReturnType());
	}
	private CppFunction parseSingleFunction(String signature){
		return parser.parseFunctions(signature).get(0);
	}

}
