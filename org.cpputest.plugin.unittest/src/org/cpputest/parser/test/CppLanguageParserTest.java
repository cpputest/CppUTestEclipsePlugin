package org.cpputest.parser.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.CppTokensToPotentialLanguagePartsTranslator;
import org.cpputest.parser.CppLikeCodeTokenSplitter;
import org.cpputest.parser.YieldParser;
import org.cpputest.parser.parts.CppPart;
import org.junit.Test;

public class CppLanguageParserTest {

	@Test
	public void testFunction() {
		List<CppPart> parts = parse("int fun(){}");
		assertEquals(5, parts.size());
		assertEquals(CppPart.StartNewFunction("int", 0), parts.get(0));
		assertEquals(CppPart.AddToFunctionName("fun"), parts.get(1));
		assertEquals(CppPart.PartOfLongFunctionName("("), parts.get(2));
		assertEquals(CppPart.EndOfFunctionSignature(")"), parts.get(3));
		assertEquals(CppPart.EndOfFunction(), parts.get(4));
	}
	@Test
	public void testSemicolonInGlobal() {
		List<CppPart> parts = parse(";int");
		assertEquals(2, parts.size());
		assertEquals(CppPart.EndOfGlobalStatement(";"), parts.get(0));
		assertEquals(CppPart.StartNewFunction("int",0), parts.get(1));
	}
	@Test
	public void testPointerType() {
		List<CppPart> parts = parse("int *");
		assertEquals(2, parts.size());
		assertEquals(CppPart.StartNewFunction("int",0), parts.get(0));
		assertEquals(CppPart.AddToFunctionName("*"), parts.get(1));
	}
	@Test
	public void testLeadWithPreprocessor() {
		List<CppPart> parts = parse("#include <abc>\nint");
		assertEquals(1, parts.size());
		assertEquals(CppPart.StartNewFunction("int",0), parts.get(0));
	}
	@Test
	public void testComments() {
		List<CppPart> parts = parse("/**/");
		assertEquals(0, parts.size());
	}
	@Test
	public void testCppComments() {
		List<CppPart> parts = parse("// /* \n void //\"\nfun(void//xxx\n)//\n;//\n");
		assertEquals(5, parts.size());
		assertEquals(CppPart.StartNewFunction("void",0), parts.get(0));
		assertEquals(CppPart.AddToFunctionName("fun"), parts.get(1));
		assertEquals(CppPart.PartOfLongFunctionName("("), parts.get(2));
		assertEquals(CppPart.Parameter("void"), parts.get(3));
		assertEquals(CppPart.EndOfFunctionSignature(")"), parts.get(4));
	}
	
	private List<CppPart> parse(String string) {
		final ArrayList<CppPart> list = new ArrayList<CppPart>();
		YieldParser yp = new YieldParser() {
			@Override
			public void yield(CppPart fun) {
				list.add(fun);
			}
		};
		
		CppTokensToPotentialLanguagePartsTranslator trans = new CppTokensToPotentialLanguagePartsTranslator(yp);
		CppLikeCodeTokenSplitter tokenizer = new CppLikeCodeTokenSplitter();
		tokenizer.generateTokensFromSourceCode(string, trans);
		
		return list;
	}

}
