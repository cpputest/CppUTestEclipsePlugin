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
		assertEquals(CppPart.StartNewFunction(Token.token("int")), parts.get(0));
	}
	@Test
	public void testSemicolonInGlobal() {
		String[] strs = {";","int"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(2, parts.size());
		assertEquals(CppPart.EndOfGlobalStatement(), parts.get(0));
		assertEquals(CppPart.StartNewFunction(Token.token("int")), parts.get(1));
	}
	@Test
	public void testFunction() {
		String[] strs = {"int", "fun", "(", ")", "{", "}"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(5, parts.size());
		assertEquals(CppPart.StartNewFunction(Token.token("int")), parts.get(0));
		assertEquals(CppPart.AddToFunctionName(Token.token("fun")), parts.get(1));
		assertEquals(CppPart.PartOfLongFunctionName(Token.token("(")), parts.get(2));
		assertEquals(CppPart.EndOfFunctionSignature(Token.token(")")), parts.get(3));
		assertEquals(CppPart.EndOfFunction(), parts.get(4));
	}
	@Test
	public void testParameter() {
		String[] strs = {"int", "fun", "(", "PARAMETER1", ")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(CppPart.Parameter(Token.token("PARAMETER1")), parts.get(3));
	}
	@Test
	public void testMultipleParameter() {
		String[] strs = {"int", "fun", "(", "TYPE1","ARG1", ",", "TYPE2","ARG2",")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(CppPart.Parameter(Token.token("TYPE1")), parts.get(3));
		assertEquals(CppPart.Parameter(Token.token("ARG1")), parts.get(4));
		assertEquals(CppPart.Parameter(Token.token(",")), parts.get(5));
		assertEquals(CppPart.Parameter(Token.token("TYPE2")), parts.get(6));
		assertEquals(CppPart.Parameter(Token.token("ARG2")), parts.get(7));
	}
	@Test
	public void testPointerParameter() {
		String[] strs = {"int", "fun", "(", "TYPE1","*", "ARG1",")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(CppPart.Parameter(Token.token("TYPE1")), parts.get(3));
		assertEquals(CppPart.Parameter(Token.token("*")), parts.get(4));
		assertEquals(CppPart.Parameter(Token.token("ARG1")), parts.get(5));
	}
	@Test
	public void testPointerType() {
		String[] strs = {"int", "*"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(2, parts.size());
		assertEquals(CppPart.StartNewFunction(Token.token("int")), parts.get(0));
		assertEquals(CppPart.AddToFunctionName(Token.token("*")), parts.get(1));
	}
	@Test
	public void testFunctionPointer() {
		String[] strs = {"int", "(","*", "fp",")","(",")"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(6, parts.size());
		assertEquals(CppPart.StartNewFunction(Token.token("int")), parts.get(0));
		assertEquals(CppPart.PartOfLongFunctionName(Token.token("(")), parts.get(1));
		assertEquals(CppPart.Parameter(Token.token("*")), parts.get(2));
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
		assertEquals(CppPart.StartNewFunction(Token.token("SHOULD_BE_ABILE_TO_SEE")), parts.get(1));
	}

	@Test
	public void testGlobalAssignment() {
		String[] strs = {"int", "a","=","b",";"};
		List<CppPart> parts = getPotentialParts(getTokens(strs));
		assertEquals(CppPart.StartNewFunction(Token.token("int")), parts.get(0));
		assertEquals(CppPart.AddToFunctionName(Token.token("a")), parts.get(1));
		assertEquals(CppPart.Assignment(), parts.get(2));
		assertEquals(CppPart.EndOfGlobalStatement(), parts.get(3));
	}
	
	private Iterable<Token> getTokens(String[] strs) {
		List<Token> tokens = new ArrayList<Token>();
		for (String s:strs)
			tokens.add(Token.token(s,0));
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
