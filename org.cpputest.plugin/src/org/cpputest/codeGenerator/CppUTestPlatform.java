package org.cpputest.codeGenerator;

import org.eclipse.jface.text.Position;

public interface CppUTestPlatform {

	String getSelectedText();

	void copyToClipboard(String string);

	void messageBox(String string);

	String getFullText();

	Position getCursorPosition();

}
