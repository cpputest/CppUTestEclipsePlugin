package org.cpputest.plugin.generaltest;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CppStubber;
import org.cpputest.parser.CppCode;
import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.parser.langunit.SignatureBuilder;
import org.junit.Test;

public class CppStubberTest {
	CppStubber stubber = new CppStubber();

	@Test
	public void testVoidFunctionWithoutParameter() {
		CppLangFunctionSignature signature = signatureBuilder()
				.withReturnType("void")
				.addToFunctionName("fun")
				.build();
		
		CppCode stub = stubber.getEmptyCStub(signature);
		
		assertEquals("void fun(){}\n", stub.toString());
	}

	@Test
	public void testFunctionWithoutParameterReturnsInt() {
		CppLangFunctionSignature signature = signatureBuilder()
				.withReturnType("int")
				.addToFunctionName("foo")
				.build();
		
		CppCode stub = stubber.getEmptyCStub(signature);
		
		assertEquals("int foo(){return 0;}\n", stub.toString());
	}
	
	private SignatureBuilder signatureBuilder() {
		return new SignatureBuilder();
	}

}
