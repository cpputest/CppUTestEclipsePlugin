package org.cpputest.plugin;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.codeGenerator.Stubber;

public interface SourceCodeStubberForEditor {
	CppCode getStubOfCodeInEditor(Stubber stubber);
}