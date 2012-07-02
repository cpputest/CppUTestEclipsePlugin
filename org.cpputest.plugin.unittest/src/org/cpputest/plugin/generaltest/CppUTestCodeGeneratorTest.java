package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import org.cpputest.plugin.general.CompactCppCodeFormater;
import org.cpputest.plugin.general.CppCode;
import org.cpputest.plugin.general.CppCodeFormater;
import org.cpputest.plugin.general.CppUTestCodeGenerator;
import org.junit.Ignore;
import org.junit.Test;

public class CppUTestCodeGeneratorTest {

	CppUTestCodeGenerator cpputest = new CppUTestCodeGenerator();
	@Test
	public void testNoFunctionFound() {
		assertEquals("",getEmptyCStrubOfCode(""));
	}
	@Test
	public void testGenerateSimpleFunction() {
		assertEquals("void foo(){}\n",getEmptyCStrubOfCode("void foo();"));
	}
	@Test
	public void testGenerateWithIntReturnType() {
		assertEquals("int foo(){return 0;}\n",getEmptyCStrubOfCode("int foo();"));
	}
	@Test
	public void testGenerateWithOtherReturnType() {
		assertEquals("BYTE foo(){return 0;}\n",getEmptyCStrubOfCode("BYTE foo();"));
	}
	@Test
	public void testGenerateWithPointerReturnType() {
		assertEquals("TYPE * foo(){return 0;}\n",getEmptyCStrubOfCode("TYPE * foo();"));
	}
	@Ignore("next")
	@Test
	public void testGenerateWithVoidPointerReturnType() {
		assertEquals("void * foo(){return 0;}\n",getEmptyCStrubOfCode("void * foo();"));
	}
	@Test
	public void testGenerateWithReferenceType() {
		assertEquals("TYPE & foo(){static TYPE t;return t;}\n",getEmptyCStrubOfCode("TYPE & foo();"));
	}
	@Test
	public void testGenerateWithParameter() {
		assertEquals("TYPE & foo(int a){static TYPE t;return t;}\n",getEmptyCStrubOfCode("TYPE & foo(int a);"));
	}
	@Test
	public void testGenerateWithVoidParameter() {
		assertEquals("void foo(void){}\n",getEmptyCStrubOfCode("void foo(void);"));
	}
	@Test
	public void testGenerateMultipleFunctions() {
		assertEquals("void foo(){}\nint bar(){return 0;}\n",getEmptyCStrubOfCode("void foo();int bar();"));
	}
	private String getEmptyCStrubOfCode(String code) {
		CppCodeFormater formater = new CompactCppCodeFormater();
		return formater.format(cpputest.getEmptyStubOfCode(code));
	}
}
