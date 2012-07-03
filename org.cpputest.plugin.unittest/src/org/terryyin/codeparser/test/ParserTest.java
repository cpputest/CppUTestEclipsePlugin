package org.terryyin.codeparser.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.terryyin.codeparser.CTokenTranslator;
import org.terryyin.codeparser.Tokenizer;
import org.terryyin.codeparser.YieldParser;
import org.terryyin.codeparser.YieldToken;
import org.terryyin.codeparser.parts.CppPart;

public class ParserTest {

	@Test
	public void testFunction() {
		List<CppPart> parts = parse("int fun(){}");
		assertEquals(5, parts.size());
	}
	private List<CppPart> parse(String string) {
		final ArrayList<CppPart> list = new ArrayList<CppPart>();
		YieldParser yp = new YieldParser() {
			@Override
			public void yield(CppPart fun) {
				list.add(fun);
			}
		};
		
		CTokenTranslator trans = new CTokenTranslator(yp);
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.generateTokensFromSourceCode(string, trans);
		
		return list;
	}

}
