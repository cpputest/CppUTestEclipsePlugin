package org.cpputest.codeGenerator.test;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.plugin.CppUTestFactory;
import org.junit.Test;

public class CppDefautlMockStubberTest {
	Stubber stubber = new CppUTestFactory().createDefaultMockStubber();

	@Test
	public void testVoidFunctionWithoutParameter() {
		CppLangFunctionSignature signature = CppLangFunctionSignature.builderStartWith("void")
				.addToFunctionDeclaration("fun")
				.build();
		
		CppCode stub = stubber.getStubOfSignature(signature);
		
		assertEquals("void fun(){mock().actualCall(\"fun\");}\n", stub.toString());
	}

}
