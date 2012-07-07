package org.cpputest.parser.test;

import static org.junit.Assert.*;

import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.parser.langunit.SignatureBuilder;
import org.junit.Test;

public class SignatureBuilderTest {
	SignatureBuilder builder = new SignatureBuilder();

	@Test
	public void testBuildFunctionWithoutParameterAndVoidReturnType() {
		CppLangFunctionSignature signature = builder
				.withReturnType("void")
				.addToFunctionName("fun").build();
		assertEquals("void fun()", signature.getCode().toString());
	}
	@Test
	public void testBuildFunctionWithoutParameterAndPointerReturnType() {
		CppLangFunctionSignature signature = builder
				.withReturnType("int")
				.addToReturnType("*")
				.addToFunctionName("fun").build();
		assertEquals("int* fun()", signature.getCode().toString());
	}

}
