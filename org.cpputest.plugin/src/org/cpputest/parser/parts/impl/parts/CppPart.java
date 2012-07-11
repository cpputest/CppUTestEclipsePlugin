package org.cpputest.parser.parts.impl.parts;

import org.cpputest.parser.impl.Token;

public class CppPart {
	public enum Type {
		MAYBE_NEW_FUNCTION, END_OF_FUNCTION_SIGNATURE, PART_OF_LONG_FUNCTION_NAME
	, MAYBE_PART_OF_FUNCTION_NAME, PARAMETER, END_OF_FUNCTION, TOKEN, END_OF_GLOBAL_STATEMENT, PREPROCESSOR, TYPEDEF, ASSIGNMENT}
	private Type type;
	private final Token token;
	private CppPart(Type type, Token token){
		this.type = type;
		this.token = token;
	}
	public Type getType(){
		return type;
	}
	public static CppPart StartNewFunction(Token token2) {
		return new CppPart(Type.MAYBE_NEW_FUNCTION, token2);
	}
	static public CppPart EndOfFunctionSignature(org.cpputest.parser.impl.Token token2){
		return new CppPart(Type.END_OF_FUNCTION_SIGNATURE, token2);
	}
	public static CppPart PartOfLongFunctionName(Token token) {
		return new CppPart(Type.PART_OF_LONG_FUNCTION_NAME, token);
	}
	public static CppPart AddToFunctionName(Token token2) {
		return new CppPart(Type.MAYBE_PART_OF_FUNCTION_NAME, token2);
	}
	public static CppPart Parameter(org.cpputest.parser.impl.Token token2) {
		return new CppPart(Type.PARAMETER, token2);
	}
	public static CppPart EndOfFunction() {
		return new CppPart(Type.END_OF_FUNCTION, null);
	}
	public static CppPart Token(org.cpputest.parser.impl.Token token2) {
		return new CppPart(Type.TOKEN, token2);
	}
	public static CppPart EndOfGlobalStatement() {
		return new CppPart(Type.END_OF_GLOBAL_STATEMENT, null);
	}
	
	public static CppPart Typedef() {
		return new CppPart(Type.TYPEDEF, null);
	}
	public static CppPart Assignment() {
		return new CppPart(Type.ASSIGNMENT, null);
	}

	@Override
	public String toString() {
		return "CppPart [type=" + type + ", token=" + token + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public String codeString() {
		return token.toString();
	}
}
