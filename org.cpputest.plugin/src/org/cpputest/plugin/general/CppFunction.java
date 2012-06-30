package org.cpputest.plugin.general;

public class CppFunction {
	private final String type;
	private final boolean isReturnReference;
	private final String signature;
	public CppFunction(String signature, String type, boolean isReturnReference) {
		this.type = type;
		this.isReturnReference = isReturnReference;
		this.signature = signature;
	}

	public String getEmptyStub() {
		if (!type.equals("void")) {
			if (isReturnReference)
				return String.format("%s{static %s t;return t;}\n",signature, type);
			return signature.concat("{return 0;}\n");
		}
		return signature.concat("{}\n");
	}

	public boolean isReturnReference() {
		return this.isReturnReference;
	}

	public Object getSignature() {
		return signature;
	}

	public Object getReturnType() {
		return type;
	}

}
