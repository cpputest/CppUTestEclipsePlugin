package org.cpputest.plugin;

import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CppUTestPlatform;
import org.cpputest.codeGenerator.Stubber;
import org.eclipse.ui.IWorkbenchWindow;

public interface ICppUTestFactory {

	CppUTestPlatform createPlatformAdaptor(IWorkbenchWindow window);

	SourceCodeStubberForEditor createSourceCodeStubber();

	CppCodeFormater createCodeFormater();

	Stubber createEmptyStubber();

	StubCodeUI createStubCodeUI();

	Stubber createDefaultMockStubber();

}
