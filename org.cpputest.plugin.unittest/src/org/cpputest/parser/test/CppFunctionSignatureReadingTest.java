package org.cpputest.parser.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator;
import org.cpputest.parser.YieldLanguageUnits;
import org.cpputest.parser.langunit.LanguageUnit;
import org.junit.Test;

public class CppFunctionSignatureReadingTest {
	List<LanguageUnit> units;
	@Test
	public void testParseEmptyString() {
		units = parse("");
		assertEquals(0, units.size());
	}
	@Test
	public void testParseIncompleteFun() {
		units = parse("int fun(");
		assertEquals(0, units.size());
	}
	@Test
	public void testParseFunctionSignature() {
		units = parse("int fun();");
		assertEquals(1, units.size());
		assertEquals("int fun()", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionSignatureWithPointerReturnType() {
		units = parse("int * fun();");
		assertEquals(1, units.size());
		assertEquals("int * fun()", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunction() {
		units = parse("int foo(){}");
		assertEquals(1, units.size());
		assertEquals("int foo()", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionParameter() {
		units = parse("void foo(int a);");
		assertEquals(1, units.size());
		assertEquals("void foo(int a)", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionWithMultipleParameter() {
		units = parse("void foo(int a, char c);");
		assertEquals(1, units.size());
		assertEquals("void foo(int a , char c)", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionWithPointerParameter() {
		units = parse("void foo(int*a);");
		assertEquals(1, units.size());
		assertEquals("void foo(int * a)", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionWithPriorCode() {
		units = parse("#include <abc>\nvoid foo(int*a);");
		assertEquals(1, units.size());
		assertEquals("void foo(int * a)", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionWithPriorMacroOnPreviousLine() {
		units = parse("LEADING\nvoid foo();");
		assertEquals(1, units.size());
		assertEquals("void foo()", units.get(0).getCode().toString());
	}

	@Test
	public void testParseFunctionWithPriorMacroOnPreviousLineWhenReturnTypeIsAPointer() {
		units = parse("extra char *ctermid(char *);");
		assertEquals("char * ctermid(char *)", units.get(0).getCode().toString());
	}	
	@Test
	public void testParseFunctionWithPriorMacroOnSameLine() {
		units = parse("const int foo();");
		assertEquals(1, units.size());
		assertEquals("const int foo()", units.get(0).getCode().toString());
	}
	@Test
	public void testParseFunctionWithCommentsInline() {
		units = parse("// /* \n void //\"\nfun(void//xxx\n)//\n;//\n");
		assertEquals(1, units.size());
		assertEquals("void fun(void)", units.get(0).getCode().toString());
	}
	@Test
	public void testFunctionPointerIsNotASignature() {
		units = parse("int	(*_close)(void *);");
		assertEquals(0, units.size());
	}
	@Test
	public void testTypeDefIsNotASignature() {
		units = parse("typedef int	(*_close)(void *);");
		assertEquals(0, units.size());
	}
	
	private List<LanguageUnit> parse(String string) {
		final ArrayList<LanguageUnit> list = new ArrayList<LanguageUnit>();
		YieldLanguageUnits yp = new YieldLanguageUnits() {
			@Override
			public void yield(LanguageUnit unit) {
				list.add(unit);
			}
		};
		
		CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator reader = new CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator(yp);
		reader.read(string);
		
		return list;
	}

}
