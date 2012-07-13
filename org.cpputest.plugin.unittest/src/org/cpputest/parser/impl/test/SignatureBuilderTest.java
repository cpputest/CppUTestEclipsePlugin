package org.cpputest.parser.impl.test;

import static org.junit.Assert.*;

import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.parser.langunit.SignatureBuilder;
import org.junit.Test;

public class SignatureBuilderTest {

	private static final int END_OFFSET = 15;
	private static final int BEGIN_OFFSET = 10;
	@Test
	public void testBuildFunctionWithoutParameterAndVoidReturnType() {
		CppLangFunctionSignature signature = new SignatureBuilder("void")
				.addToFunctionDeclaration("fun").build();
		assertEquals("void fun()", signature.getCode().toString());
	}
	@Test
	public void testBuildFunctionWithoutParameterAndPointerReturnType() {
		CppLangFunctionSignature signature = new SignatureBuilder("int")
				.addToFunctionDeclaration("*")
				.addToFunctionDeclaration("fun").build();
		assertEquals("int * fun()", signature.getCode().toString());
	}
	@Test
	public void testMoveFunctionNameToReturnTypeWhenAddToFunctionNameMoreThanOnce() {
		CppLangFunctionSignature signature = new SignatureBuilder("MACRO")
				.addToFunctionDeclaration("int")
				.addToFunctionDeclaration("fun")
				.build();
		assertEquals("MACRO int fun()", signature.getCode().toString());
	}
	@Test
	public void testConstReturnType() {
		CppLangFunctionSignature signature = new SignatureBuilder("const")
				.addToFunctionDeclaration("int")
				.addToFunctionDeclaration("fun")
				.build();
		assertEquals("fun", signature.getFunctionName());
		assertEquals("const int fun()", signature.getCode().toString());
	}
	@Test
	public void testExtraStuffBeforePointerReturnType() {
		CppLangFunctionSignature signature = new SignatureBuilder("EXTRA")
				.addToFunctionDeclaration("int")
				.addToFunctionDeclaration("*")
				.addToFunctionDeclaration("fun")
				.build();
		assertEquals("fun", signature.getFunctionName());
		assertEquals("EXTRA int * fun()", signature.getCode().toString());
	}
	@Test
	public void testBuildWithOffsetInforamtion() {
		CppLangFunctionSignature signature = new SignatureBuilder("void")
				.withBeginOffset(BEGIN_OFFSET)
				.addToFunctionDeclaration("foo")
				.withEndOffset(END_OFFSET)
				.build();
		assertFalse(signature.isOffsetInclusive(BEGIN_OFFSET - 1));
		assertTrue(signature.isOffsetInclusive(BEGIN_OFFSET));
		assertTrue(signature.isOffsetInclusive(END_OFFSET));
		assertFalse(signature.isOffsetInclusive(END_OFFSET + 1));
	}

}
