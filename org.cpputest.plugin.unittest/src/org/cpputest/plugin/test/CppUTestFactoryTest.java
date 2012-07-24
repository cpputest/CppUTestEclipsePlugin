package org.cpputest.plugin.test;

import static org.junit.Assert.*;

import org.cpputest.codeGenerator.CompactCppCodeFormater;
import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppDefaultMockStubber;
import org.cpputest.codeGenerator.CppEmptyStubber;
import org.cpputest.codeGenerator.CppUTestPlatform;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.plugin.CppUTestEclipsePlatform;
import org.cpputest.plugin.CppUTestFactory;
import org.cpputest.plugin.CppUTestSourceCodeStubberForEditor;
import org.cpputest.plugin.CppUTestStubCodeUI;
import org.cpputest.plugin.SourceCodeStubberForEditor;
import org.cpputest.plugin.StubCodeUI;
import org.junit.Test;

public class CppUTestFactoryTest {
	final CppUTestFactory factory = new CppUTestFactory();
	
	@Test
	public void testCreatePlatform() {
		CppUTestPlatform platform = factory.createPlatformAdaptor(null);
		assertTrue(CppUTestEclipsePlatform.class.isInstance(platform));
	}
	
	@Test
	public void testCreateSourceCodeStubber() {
		SourceCodeStubberForEditor obj = factory.createSourceCodeStubber();
		assertTrue(CppUTestSourceCodeStubberForEditor.class.isInstance(obj));
	}
	
	@Test
	public void testCreateCodeFormater() {
		CppCodeFormater obj = factory.createCodeFormater();
		assertTrue(CompactCppCodeFormater.class.isInstance(obj));
	}

	@Test
	public void testCreateEmptyStubber() {
		Stubber obj = factory.createEmptyStubber();
		assertTrue(CppEmptyStubber.class.isInstance(obj));
	}

	@Test
	public void testCreateStubCodeUI() {
		StubCodeUI obj = factory.createStubCodeUI();
		assertTrue(CppUTestStubCodeUI.class.isInstance(obj));
	}
	
	@Test
	public void testCreateDefaultMock() {
		Stubber obj = factory.createDefaultMockStubber();
		assertTrue(CppDefaultMockStubber.class.isInstance(obj));
	}
}
