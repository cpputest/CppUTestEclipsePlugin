package org.cpputest.codeGenerator;

public interface CppUTestPlatform {

	String getSelectedText();

	void copyToClipboard(String string);

	void messageBox(String string);

}
