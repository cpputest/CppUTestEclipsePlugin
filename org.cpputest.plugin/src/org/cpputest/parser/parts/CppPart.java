package org.cpputest.parser.parts;

public class CppPart {
	public enum Type {
		NEW_FUNCTION, END_OF_FUNCTION_SIGNATURE, PART_OF_LONG_FUNCTION_NAME
	, ADD_TO_FUNCTION_NAME, PARAMETER, END_OF_FUNCTION, TOKEN, END_OF_GLOBAL_STATEMENT, PREPROCESSOR}
	private Type type;
	private final String token;
	private final int currentLine;
	private CppPart(Type type, String token, int currentLine){
		this.type = type;
		this.token = token;
		this.currentLine = currentLine;
	}
	public Type getType(){
		return type;
	}
	public static CppPart StartNewFunction(String token, int currentLine) {
		return new CppPart(Type.NEW_FUNCTION, token, currentLine);
	}
	static public CppPart EndOfFunctionSignature(String token){
		return new CppPart(Type.END_OF_FUNCTION_SIGNATURE, token, 0);
	}
	public static CppPart PartOfLongFunctionName(String token) {
		return new CppPart(Type.PART_OF_LONG_FUNCTION_NAME, token, 0);
	}
	public static CppPart AddToFunctionName(String token) {
		return new CppPart(Type.ADD_TO_FUNCTION_NAME, token, 0);
	}
	public static CppPart Parameter(String token) {
		return new CppPart(Type.PARAMETER, token, 0);
	}
	public static CppPart EndOfFunction() {
		return new CppPart(Type.END_OF_FUNCTION, "", 0);
	}
	public static CppPart Token(String token) {
		return new CppPart(Type.TOKEN, token, 0);
	}
	public static CppPart EndOfGlobalStatement(String token) {
		return new CppPart(Type.END_OF_GLOBAL_STATEMENT, token, 0);
	}
	public static CppPart Preprocessor(String token) {
		return new CppPart(Type.PREPROCESSOR, token, 0);
	}
	@Override
	public String toString() {
		return token;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentLine;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CppPart other = (CppPart) obj;
		if (currentLine != other.currentLine)
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
