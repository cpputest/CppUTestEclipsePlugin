package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import org.cpputest.plugin.general.CppUTestCodeGeneratorImpl;
import org.junit.Ignore;
import org.junit.Test;

public class CppUTestCodeGeneratorTest {

	@Test
	public void testGenerateSimpleFunction() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("void foo(){}\n",cpputest.getEmptyStubOfCode("void foo();"));
	}
	@Test
	public void testGenerateWithIntReturnType() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("int foo(){return 0;}\n",cpputest.getEmptyStubOfCode("int foo();"));
	}
	@Ignore("still under development")
	public void testGenerateWithVoidParameter() {
		CppUTestCodeGeneratorImpl cpputest = new CppUTestCodeGeneratorImpl();
		assertEquals("void foo(){}\n",cpputest.getEmptyStubOfCode("void foo(void);"));
	}

}
