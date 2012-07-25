package org.cpputest.plugin;

import org.cpputest.codeGenerator.CppCodeFormater;
import org.cpputest.codeGenerator.CUTPlatformAdaptor;
import org.cpputest.codeGenerator.Stubber;
import org.eclipse.ui.IWorkbenchWindow;

public interface ICppUTestFactory {

	CUTPlatformAdaptor createPlatformAdaptor(IWorkbenchWindow window);

	SourceCodeStubberForEditor createSourceCodeStubber();

	CppCodeFormater createCodeFormater();

	Stubber createEmptyStubber();

	StubCodeUI createStubCodeUI();

	Stubber createDefaultMockStubber();

}
