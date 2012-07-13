package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.CppStubber;
import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.junit.Test;

public class CppStubberTest {
	CppStubber stubber = new CppStubber();

	@Test
	public void testVoidFunctionWithoutParameter() {
		CppLangFunctionSignature signature = CppLangFunctionSignature.builderStartWith("void")
				.addToFunctionDeclaration("fun")
				.build();
		
		CppCode stub = stubber.getEmptyCStub(signature);
		
		assertEquals("void fun(){}\n", stub.toString());
	}

	@Test
	public void testFunctionWithoutParameterReturnsInt() {
		CppLangFunctionSignature signature = CppLangFunctionSignature.builderStartWith("int")
				.addToFunctionDeclaration("foo")
				.build();
		
		CppCode stub = stubber.getEmptyCStub(signature);
		
		assertEquals("int foo(){return 0;}\n", stub.toString());
	}
	
}
