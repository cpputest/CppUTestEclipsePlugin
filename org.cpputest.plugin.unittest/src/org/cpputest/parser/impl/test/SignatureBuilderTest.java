package org.cpputest.parser.impl.test;

import static org.junit.Assert.*;

import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.parser.langunit.SignatureBuilder;
import org.junit.Test;

public class SignatureBuilderTest {

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
		assertEquals("int fun()", signature.getCode().toString());
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
		assertEquals("int * fun()", signature.getCode().toString());
	}

}
