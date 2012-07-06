package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CppUTestCodeGenerator;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.parser.CppCode;
import org.cpputest.parser.SourceCodeReader;
import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class CppUTestCodeGeneratorTest {
	final String SOURCE_CODE = "code";
	final CppCode code1 = new CppCode("expected ");
	final CppCode code2 = new CppCode("stub code");
	final CppCode expected_code = new CppCode("expected stub code");
	Mockery context = new JUnit4Mockery();
	
	@Test
	public void testGenerateEmpty() {
		final SourceCodeReader reader = context.mock(SourceCodeReader.class);
		final Stubber stubber = context.mock(Stubber.class);
		final Iterable<?> units = context.mock(Iterable.class);
		final CppLangFunctionSignature s1 = new CppLangFunctionSignature(null);
		final CppLangFunctionSignature s2 = new CppLangFunctionSignature(null);
		
		context.checking(new Expectations() {{
			oneOf(reader).signatures(SOURCE_CODE); will(returnValue(units));
			oneOf(units).iterator(); will(returnIterator(s1,s2));
			oneOf(stubber).getEmptyCStub(s1); will(returnValue(code1));
			oneOf(stubber).getEmptyCStub(s2); will(returnValue(code2));
		}});
		
		CppUTestCodeGenerator cpputest = new CppUTestCodeGenerator(reader, stubber);
		assertEquals(expected_code, cpputest.getEmptyStubOfCode(SOURCE_CODE));
	}
//	public void testNoFunctionFound() {
//		assertEquals("",getEmptyCStrubOfCode(""));
//	}
//	@Test
//	public void testGenerateSimpleFunction() {
//		assertEquals("void foo(){}\n",getEmptyCStrubOfCode("void foo();"));
//	}
//	@Test
//	public void testGenerateWithIntReturnType() {
//		assertEquals("int foo(){return 0;}\n",getEmptyCStrubOfCode("int foo();"));
//	}
//	@Test
//	public void testGenerateWithOtherReturnType() {
//		assertEquals("BYTE foo(){return 0;}\n",getEmptyCStrubOfCode("BYTE foo();"));
//	}
//	@Test
//	public void testGenerateWithPointerReturnType() {
//		assertEquals("TYPE * foo(){return 0;}\n",getEmptyCStrubOfCode("TYPE * foo();"));
//	}
//	@Ignore("next")
//	@Test
//	public void testGenerateWithVoidPointerReturnType() {
//		assertEquals("void * foo(){return 0;}\n",getEmptyCStrubOfCode("void * foo();"));
//	}
//	@Test
//	public void testGenerateWithReferenceType() {
//		assertEquals("TYPE & foo(){static TYPE t;return t;}\n",getEmptyCStrubOfCode("TYPE & foo();"));
//	}
//	@Test
//	public void testGenerateWithParameter() {
//		assertEquals("TYPE & foo(int a){static TYPE t;return t;}\n",getEmptyCStrubOfCode("TYPE & foo(int a);"));
//	}
//	@Test
//	public void testGenerateWithVoidParameter() {
//		assertEquals("void foo(void){}\n",getEmptyCStrubOfCode("void foo(void);"));
//	}
//	@Test
//	public void testGenerateMultipleFunctions() {
//		assertEquals("void foo(){}\nint bar(){return 0;}\n",getEmptyCStrubOfCode("void foo();int bar();"));
//	}
}
