package org.cpputest.parser.impl.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.impl.CppLikeCodeTokenSplitter;
import org.cpputest.parser.impl.Token;
import org.junit.Test;

public class CppLikeCodeTokenSplitterTest {

	@Test
	public void testEmpty() {
		assertEquals(0, tokenize("").size());
	}
	@Test
	public void testSpaceOnly() {
		List<Token> tokens = tokenize("  \n  \t ");
		assertEquals(0, tokens.size());
	}
	@Test
	public void testKeyword() {
		List<Token> tokens = tokenize("int");
		assertEquals(1, tokens.size());
		assertEquals("int", tokens.get(0).toString());
	}
	@Test
	public void firstTokenOffsetShouldBe0() {
		List<Token> tokens = tokenize("int");
		assertEquals(0, tokens.get(0).getBeginOffset());
	}
	@Test
	public void firstTokenOffsetShouldBe1IfThereIsASpaceBeforeIt() {
		List<Token> tokens = tokenize(" int");
		assertEquals(1, tokens.get(0).getBeginOffset());
	}
	@Test
	public void testFunction() {
		List<Token> tokens = tokenize("int fun(){}");
		assertEquals(6, tokens.size());
		assertEquals("fun", tokens.get(1).toString());
	}
	@Test
	public void testComment() {
		List<Token> tokens = tokenize("/*/* \n int*/");
		assertEquals(1, tokens.size());
		assertEquals("/*/* \n int*/", tokens.get(0).toString());
	}
	@Test
	public void testCppComment() {
		List<Token> tokens = tokenize("int  //comment \n  fun");
		assertEquals(3, tokens.size());
	}
	@Test
	public void testPreprocess() {
		List<Token> tokens = tokenize("#preprocess line\nint");
		assertEquals(1, tokens.size());
		assertEquals("int", tokens.get(0).toString());
	}
	@Test
	public void testMultipleLinePreprocess() {
		List<Token> tokens = tokenize("#preprocess line1\\\nline2\nint");
		assertEquals(1, tokens.size());
		assertEquals("int", tokens.get(0).toString());
	}
	@Test
	public void testMultipleLinePreprocess2() {
		List<Token> tokens = tokenize("#endif\n\n#endif\n");
		assertEquals(0, tokens.size());
	}
	@Test
	public void testVarArgs() {
		List<Token> tokens = tokenize("...");
		assertEquals(1, tokens.size());
	}
	
	private List<Token> tokenize(String string) {
		final ArrayList<Token> list = new ArrayList<Token>();
		
		CppLikeCodeTokenSplitter tokenizer = new CppLikeCodeTokenSplitter();
		for (Token token: tokenizer.tokens(string))
			list.add(token);
		
		return list;
	}

}
