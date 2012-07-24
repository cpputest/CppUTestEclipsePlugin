package org.cpputest.plugin.test;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.plugin.CppUTestFactory;
import org.junit.Test;

public class CppEmptyStubberTest {
	Stubber stubber = new CppUTestFactory().createEmptyStubber();

	@Test
	public void testVoidFunctionWithoutParameter() {
		CppLangFunctionSignature signature = CppLangFunctionSignature.builderStartWith("void")
				.addToFunctionDeclaration("fun")
				.build();
		
		CppCode stub = stubber.getStubOfSignature(signature);
		
		assertEquals("void fun(){}\n", stub.toString());
	}

	@Test
	public void testFunctionWithoutParameterReturnsInt() {
		CppLangFunctionSignature signature = CppLangFunctionSignature.builderStartWith("int")
				.addToFunctionDeclaration("foo")
				.build();
		
		CppCode stub = stubber.getStubOfSignature(signature);
		
		assertEquals("int foo(){return 0;}\n", stub.toString());
	}
	
}
