package org.cpputest.plugin.test;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.CppUTestStubCreator;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.parser.SourceCodeReader;
import org.cpputest.parser.impl.Token;
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
		final CppLangFunctionSignature s1 = CppLangFunctionSignature.builderStartWith(null).build();
		final CppLangFunctionSignature s2 = CppLangFunctionSignature.builderStartWith(null).build();
		
		context.checking(new Expectations() {{
			oneOf(reader).signatures(SOURCE_CODE); will(returnValue(units));
			oneOf(units).iterator(); will(returnIterator(s1,s2));
			oneOf(stubber).getStubOfSignature(s1); will(returnValue(code1));
			oneOf(stubber).getStubOfSignature(s2); will(returnValue(code2));
		}});
		
		CppUTestStubCreator cpputest = new CppUTestStubCreator(reader);
		assertEquals(expected_code, cpputest.getStubOfCode(SOURCE_CODE,stubber));
	}
	@Test
	public void testGenerateEmptyAtPosition() {
		final int OFFSET = 10;
		final SourceCodeReader reader = context.mock(SourceCodeReader.class);
		final Stubber stubber = context.mock(Stubber.class);
		final Iterable<?> units = context.mock(Iterable.class);
		final CppLangFunctionSignature s1 = CppLangFunctionSignature.builderStartWith("void")
				.withBeginOffset(OFFSET-4)
				.addToFunctionDeclaration(Token.token("foo"))
				.withEndOffset(OFFSET - 2)
				.build();
		final CppLangFunctionSignature s2 = CppLangFunctionSignature.builderStartWith("void")
				.withBeginOffset(OFFSET-1)
				.addToFunctionDeclaration(Token.token("bar"))
				.withEndOffset(OFFSET + 1)
				.build();
		context.checking(new Expectations() {{
			oneOf(reader).signatures(SOURCE_CODE); will(returnValue(units));
			oneOf(units).iterator(); will(returnIterator(s1,s2));
			oneOf(stubber).getStubOfSignature(s2); will(returnValue(code2));
		}});
		
		CppUTestStubCreator cpputest = new CppUTestStubCreator(reader);
		assertEquals(code2, cpputest.getStubOfCodeAtPosition(SOURCE_CODE, OFFSET, stubber));
	}
}
