package org.cpputest.plugin.general;

public class CppFunction {
	private final String type;
	private final boolean isReturnReference;
	public CppFunction(String type, boolean isReturnReference) {
		this.type = type;
		this.isReturnReference = isReturnReference;
	}

	public String getEmptyStub(String string) {
		if (!type.equals("void")) {
			if (isReturnReference)
				return string.replaceFirst(";",
						String.format("{static %s t;return t;}\n", type));
			return string.replaceFirst(";", "{return 0;}\n");
		}
		return string.replaceFirst(";", "{}\n");
	}

	public boolean isReturnReference() {
		return this.isReturnReference;
	}

}
