package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;
import org.cpputest.plugin.general.CppUTestCodeGeneratorImpl;
import org.junit.Ignore;
import org.junit.Test;

public class CppUTestCodeGeneratorTest {

	@Test
	public void testNoFunctionFound() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("",cpputest.getEmptyCStubOfCode(""));
	}
	@Test
	public void testGenerateSimpleFunction() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("void foo(){}\n",cpputest.getEmptyCStubOfCode("void foo();"));
	}
	@Test
	public void testGenerateWithIntReturnType() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("int foo(){return 0;}\n",cpputest.getEmptyCStubOfCode("int foo();"));
	}
	@Test
	public void testGenerateWithOtherReturnType() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("BYTE foo(){return 0;}\n",cpputest.getEmptyCStubOfCode("BYTE foo();"));
	}
	@Test
	public void testGenerateWithPointerReturnType() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("TYPE * foo(){return 0;}\n",cpputest.getEmptyCStubOfCode("TYPE * foo();"));
	}
	@Test
	public void testGenerateWithReferenceType() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("TYPE & foo(){static TYPE t;return t;}\n",cpputest.getEmptyCStubOfCode("TYPE & foo();"));
	}
	@Test
	public void testGenerateWithParameter() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("TYPE & foo(int a){static TYPE t;return t;}\n",cpputest.getEmptyCStubOfCode("TYPE & foo(int a);"));
	}
	@Test
	public void testGenerateWithVoidParameter() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("void foo(void){}\n",cpputest.getEmptyCStubOfCode("void foo(void);"));
	}
	@Test
	public void testGenerateMultipleFunctions() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("void foo(){}\nint bar(){return 0;}\n",cpputest.getEmptyCStubOfCode("void foo();int bar();"));
	}

}
