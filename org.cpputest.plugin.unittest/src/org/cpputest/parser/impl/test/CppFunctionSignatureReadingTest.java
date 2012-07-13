package org.cpputest.parser.impl.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.impl.CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator;
import org.cpputest.parser.impl.Token;
import org.cpputest.parser.langunit.LanguageUnit;
import org.cpputest.parser.parts.impl.parts.CppPart;
import org.junit.Test;

public class CppFunctionSignatureReadingTest {
	private static final int BEGIN_OFFSET = 11;
	private static final int END_OFFSET = 18;
	List<LanguageUnit> units;
	List<CppPart> parts = new ArrayList<CppPart>(); 
	@Test
	public void testParseEmptyString() {
		units = getLanguageUnits(parts);
		assertEquals(0, units.size());
	}
	@Test
	public void testParseIncompleteFun() {
		parts.add(CppPart.StartNewFunction(Token.token("int")));
		parts.add(CppPart.AddToFunctionName(Token.token("fun")));
		parts.add(CppPart.PartOfLongFunctionName(Token.token("(")));
		units = getLanguageUnits(parts);
		assertEquals(0, units.size());
	}
	@Test
	public void testParseFunctionSignature() {
		parts.add(CppPart.StartNewFunction(Token.token("int")));
		parts.add(CppPart.AddToFunctionName(Token.token("fun")));
		parts.add(CppPart.PartOfLongFunctionName(Token.token("(")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")")));
		units = getLanguageUnits(parts);
		assertEquals(1, units.size());
		assertEquals("int fun()", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionSignatureWithPointerReturnType() {
		parts.add(CppPart.StartNewFunction(Token.token("int")));
		parts.add(CppPart.AddToFunctionName(Token.token("*")));
		parts.add(CppPart.AddToFunctionName(Token.token("fun")));
		parts.add(CppPart.PartOfLongFunctionName(Token.token("(")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")")));
		units = getLanguageUnits(parts);
		assertEquals(1, units.size());
		assertEquals("int * fun()", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionParameter() {
		parts.add(CppPart.StartNewFunction(Token.token("void")));
		parts.add(CppPart.AddToFunctionName(Token.token("foo")));
		parts.add(CppPart.PartOfLongFunctionName(Token.token("(")));
		parts.add(CppPart.Parameter(Token.token("int")));
		parts.add(CppPart.Parameter(Token.token("a")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")")));
		units = getLanguageUnits(parts);
		assertEquals(1, units.size());
		assertEquals("void foo(int a)", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionWithPriorMacroOnPreviousLine() {
		parts.add(CppPart.StartNewFunction(Token.token("EXTRA")));
		parts.add(CppPart.AddToFunctionName(Token.token("void")));
		parts.add(CppPart.AddToFunctionName(Token.token("foo")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")")));
		units = getLanguageUnits(parts);
		assertEquals(1, units.size());
		assertEquals("EXTRA void foo()", units.get(0).getCode().toString());
	}

	@Test
	public void testParseFunctionWithPriorMacroOnPreviousLineWhenReturnTypeIsAPointer() {
		parts.add(CppPart.StartNewFunction(Token.token("EXTRA")));
		parts.add(CppPart.AddToFunctionName(Token.token("CHAR")));
		parts.add(CppPart.AddToFunctionName(Token.token("*")));
		parts.add(CppPart.AddToFunctionName(Token.token("foo")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")")));
		units = getLanguageUnits(parts);
		assertEquals("EXTRA CHAR * foo()", units.get(0).getCode().toString());
	}	
	@Test
	public void testParseFunctionWithPriorMacroOnSameLine() {
		parts.add(CppPart.StartNewFunction(Token.token("const")));
		parts.add(CppPart.AddToFunctionName(Token.token("int")));
		parts.add(CppPart.AddToFunctionName(Token.token("foo")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")")));
		units = getLanguageUnits(parts);
		assertEquals(1, units.size());
		assertEquals("const int foo()", units.get(0).getCode().toString());
	}
	@Test
	public void testFunctionPointerIsNotASignature() {
		parts.add(CppPart.StartNewFunction(Token.token("int")));
		parts.add(CppPart.Parameter(Token.token("*")));
		parts.add(CppPart.Parameter(Token.token("foo")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")")));
		units = getLanguageUnits(parts);
		assertEquals(0, units.size());
	}
	@Test
	public void testBeginAndEndOffsetOfFunctionSignature() {
		parts.add(CppPart.StartNewFunction(Token.token("int", BEGIN_OFFSET)));
		parts.add(CppPart.AddToFunctionName(Token.token("foo")));
		parts.add(CppPart.EndOfFunctionSignature(Token.token(")", END_OFFSET)));
		LanguageUnit unit = getLanguageUnits(parts).get(0);
		assertFalse(unit.isOffsetInclusive(BEGIN_OFFSET-1));
		assertTrue(unit.isOffsetInclusive(BEGIN_OFFSET));
		assertFalse(unit.isOffsetInclusive(END_OFFSET+1));
		
	}
	
	private List<LanguageUnit> getLanguageUnits(Iterable<CppPart> parts) {
		final ArrayList<LanguageUnit> list = new ArrayList<LanguageUnit>();
		CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator reader = new CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator();
		for (LanguageUnit unit : reader.languageUnits(parts))
			list.add(unit);
		return list;
	}
}
