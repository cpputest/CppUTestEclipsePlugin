package org.cpputest.plugin;

import org.cpputest.codeGenerator.CompactCppCodeFormater;
import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppDefaultMockStubber;
import org.cpputest.codeGenerator.CppEmptyStubber;
import org.cpputest.codeGenerator.CUTPlatformAdaptor;
import org.cpputest.codeGenerator.CppUTestStubCreator;
import org.cpputest.codeGenerator.Stubber;
import org.cpputest.parser.CppSourceCodeReader;
import org.eclipse.ui.IWorkbenchWindow;

public class CppUTestFactory implements ICppUTestFactory {
	private CppUTestEclipsePlatform platform = null;
//	static public CppUTestActionRunnerForEclipseActions createCppUTestCodeGeneratorActions(IWorkbenchWindow window) {
//		CppSourceCodeReader reader = new CppSourceCodeReader();
//		CppStubber stubber = new CppStubber();
//		CppUTestEclipsePlatform eclipse = new CppUTestEclipsePlatform(window);
//		CppUTestStubCreator codeGenerator = new CppUTestStubCreator(reader, stubber);
//		SourceCodeStubber sourceCodeStubber = new CppUTestSourceCodeStubber(eclipse, codeGenerator);
//		CompactCppCodeFormater formater = new CompactCppCodeFormater();
//		return new CppUTestActionRunnerForEclipseActions(new CppUTestStubbingActions(eclipse, sourceCodeStubber, formater));
//	}

	@Override
	public CUTPlatformAdaptor createPlatformAdaptor(IWorkbenchWindow window) {
		platform = new CppUTestEclipsePlatform(window);
		return platform;
	}

	@Override
	public SourceCodeStubberForEditor createSourceCodeStubber() {
		CppSourceCodeReader reader = new CppSourceCodeReader();
		CppUTestStubCreator codeGenerator = new CppUTestStubCreator(reader);
		return new CppUTestSourceCodeStubberForEditor(platform, codeGenerator);
	}

	@Override
	public CppCodeFormater createCodeFormater() {
		return new CompactCppCodeFormater();
	}

	@Override
	public Stubber createEmptyStubber() {
		return new CppEmptyStubber();
	}

	@Override
	public StubCodeUI createStubCodeUI() {
		return new CppUTestStubCodeUI(platform, createSourceCodeStubber(), createCodeFormater());
	}

	@Override
	public Stubber createDefaultMockStubber() {
		return new CppDefaultMockStubber();
	}
}
