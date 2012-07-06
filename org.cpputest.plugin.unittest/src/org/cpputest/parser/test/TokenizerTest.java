package org.cpputest.parser.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.Tokenizer;
import org.cpputest.parser.YieldToken;
import org.junit.Test;

public class TokenizerTest {

	@Test
	public void testEmpty() {
		assertEquals(0, tokenize("").size());
	}
	@Test
	public void testSpaceOnly() {
		List<String> tokens = tokenize("  \n  \t ");
		assertEquals(0, tokens.size());
	}
	@Test
	public void testKeyword() {
		List<String> tokens = tokenize("int");
		assertEquals(1, tokens.size());
		assertEquals("int", tokens.get(0));
	}
	@Test
	public void testFunction() {
		List<String> tokens = tokenize("int fun(){}");
		assertEquals(6, tokens.size());
		assertEquals("fun", tokens.get(1));
	}
	@Test
	public void testComment() {
		List<String> tokens = tokenize("/*/* \n int*/");
		assertEquals(1, tokens.size());
		assertEquals("/*/* \n int*/", tokens.get(0));
	}
	@Test
	public void testCppComment() {
		List<String> tokens = tokenize("int  //comment \n  fun");
		assertEquals(3, tokens.size());
	}

	private List<String> tokenize(String string) {
		final ArrayList<String> list = new ArrayList<String>();
		YieldToken yt = new YieldToken() {
			public void yield(String token, int line){
				list.add(token);
			}
		};
		
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.generateTokensFromSourceCode(string, yt);
		return list;
	}

}
