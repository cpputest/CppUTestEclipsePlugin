package org.cpputest.parser.test;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.cpputest.parser.CppSourceCodeReader;
import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.junit.Test;

public class CppSourceCodeReaderTest {
	private static final String EMPTY_SOURCE_CODE = "";
	@Test
	public void testShouldReturnEmptyIteratorReadSignatureFromEmptyString() {
		CppSourceCodeReader reader = new CppSourceCodeReader();
		Iterable<?> iterable = reader.signatures(EMPTY_SOURCE_CODE);
		Iterator<?> it = iterable.iterator();
		assertFalse(it.hasNext());
	}
	@Test
	public void testShouldReturnOneIteratorReadSignatureFromStringWithOneFunction() {
		CppSourceCodeReader reader = new CppSourceCodeReader();
		Iterable<CppLangFunctionSignature> iterable = reader.signatures("void fun();");
		Iterator<CppLangFunctionSignature> it = iterable.iterator();
		assertTrue(it.hasNext());
		assertEquals("void fun()", it.next().getCode().toString());
	}

}
