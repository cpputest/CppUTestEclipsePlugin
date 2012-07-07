package org.cpputest.parser.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.CppLanguageUnitReader;
import org.cpputest.parser.YieldLanguageUnits;
import org.cpputest.parser.langunit.LanguageUnit;
import org.junit.Ignore;
import org.junit.Test;

public class CppLanguageUnitsTest {
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
	private List<LanguageUnit> parse(String string) {
		final ArrayList<LanguageUnit> list = new ArrayList<LanguageUnit>();
		YieldLanguageUnits yp = new YieldLanguageUnits() {
			@Override
			public void yield(LanguageUnit unit) {
				list.add(unit);
			}
		};
		
		CppLanguageUnitReader reader = new CppLanguageUnitReader(yp);
		reader.read(string);
		
		return list;
	}

}
