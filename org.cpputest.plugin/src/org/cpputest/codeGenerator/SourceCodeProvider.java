package org.cpputest.codeGenerator;

public interface SourceCodeProvider {

	public abstract String getFullText();

	public abstract int getCursorPosition();

	public abstract String getSelectedText();

}