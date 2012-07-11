package org.cpputest.parser.impl.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.impl.CppTokensToPotentialLanguagePartsTranslator;
import org.cpputest.parser.impl.Token;
import org.cpputest.parser.parts.impl.parts.CppPart;
import org.junit.Test;

public class PotentialLanguagePartsTest {

	@Test
	public void testEmptyCode() {
		List<CppPart> parts = getPotentialParts(new ArrayList<Token>());
		assertEquals(0, parts.size());
	}
	@Test
	public void testPotentialReturnType() {
		String[] strs = {"int"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(1, parts.size());
		assertEquals(CppPart.StartNewFunction(new Token("int")), parts.get(0));
	}
	@Test
	public void testSemicolonInGlobal() {
		String[] strs = {";","int"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(2, parts.size());
		assertEquals(CppPart.EndOfGlobalStatement(new Token(";")), parts.get(0));
		assertEquals(CppPart.StartNewFunction(new Token("int")), parts.get(1));
	}
	@Test
	public void testFunction() {
		String[] strs = {"int", "fun", "(", ")", "{", "}"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(5, parts.size());
		assertEquals(CppPart.StartNewFunction(new Token("int")), parts.get(0));
		assertEquals(CppPart.AddToFunctionName(new Token("fun")), parts.get(1));
		assertEquals(CppPart.PartOfLongFunctionName(new Token("(")), parts.get(2));
		assertEquals(CppPart.EndOfFunctionSignature(new Token(")")), parts.get(3));
		assertEquals(CppPart.EndOfFunction(), parts.get(4));
	}
	@Test
	public void testParameter() {
		String[] strs = {"int", "fun", "(", "PARAMETER1", ")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(CppPart.Parameter(new Token("PARAMETER1")), parts.get(3));
	}
	@Test
	public void testMultipleParameter() {
		String[] strs = {"int", "fun", "(", "TYPE1","ARG1", ",", "TYPE2","ARG2",")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(CppPart.Parameter(new Token("TYPE1")), parts.get(3));
		assertEquals(CppPart.Parameter(new Token("ARG1")), parts.get(4));
		assertEquals(CppPart.Parameter(new Token(",")), parts.get(5));
		assertEquals(CppPart.Parameter(new Token("TYPE2")), parts.get(6));
		assertEquals(CppPart.Parameter(new Token("ARG2")), parts.get(7));
	}
	@Test
	public void testPointerParameter() {
		String[] strs = {"int", "fun", "(", "TYPE1","*", "ARG1",")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(CppPart.Parameter(new Token("TYPE1")), parts.get(3));
		assertEquals(CppPart.Parameter(new Token("*")), parts.get(4));
		assertEquals(CppPart.Parameter(new Token("ARG1")), parts.get(5));
	}
	@Test
	public void testPointerType() {
		String[] strs = {"int", "*"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(2, parts.size());
		assertEquals(CppPart.StartNewFunction(new Token("int")), parts.get(0));
		assertEquals(CppPart.AddToFunctionName(new Token("*")), parts.get(1));
	}
	@Test
	public void testFunctionPointer() {
		String[] strs = {"int", "(","*", "fp",")","(",")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(6, parts.size());
		assertEquals(CppPart.StartNewFunction(new Token("int")), parts.get(0));
		assertEquals(CppPart.PartOfLongFunctionName(new Token("(")), parts.get(1));
		assertEquals(CppPart.Parameter(new Token("*")), parts.get(2));
	}
	@Test
	public void testTypeDef() {
		String[] strs = {"typedef"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(1, parts.size());
		assertEquals(CppPart.Typedef(), parts.get(0));
	}
	@Test
	public void testTypeDefContentShouldBeIgnored() {
		String[] strs = {"typedef","SHOULD_BE_IGNORED", "(","*","UNTIL_SEMICOLON",")","(",")",";","SHOULD_BE_ABILE_TO_SEE"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(2, parts.size());
		assertEquals(CppPart.StartNewFunction(new Token("SHOULD_BE_ABILE_TO_SEE")), parts.get(1));
	}
	
	private Iterable<Token> getTokens(String[] strs) {
		List<Token> tokens = new ArrayList<Token>();
		for (String s:strs)
			tokens.add(new Token(s,0));
		return tokens;
	}
	private List<CppPart> getPotentialParts(Iterable<Token> tokens) {
		final ArrayList<CppPart> list = new ArrayList<CppPart>();
		CppTokensToPotentialLanguagePartsTranslator trans = new CppTokensToPotentialLanguagePartsTranslator();
		for (CppPart part: trans.getPotentialLanguageParts(tokens))
			list.add(part);
		return list;
	}

}
