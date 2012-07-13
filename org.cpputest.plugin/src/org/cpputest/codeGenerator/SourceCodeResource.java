package org.cpputest.codeGenerator;

public interface SourceCodeResource {

	public abstract String getFullText();

	public abstract int getCursorPosition();

	public abstract String getSelectedText();

}