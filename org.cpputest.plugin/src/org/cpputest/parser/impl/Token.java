package org.cpputest.parser.impl;

public final class Token {
	public static final int UNDEFINED_OFFSET = -1;
	private final String token;
	private final int offset;

	protected Token(String token, int offset) {
		this.token = token;
		this.offset = offset;
	}

	@Override
	public String toString() {
		return token;
	}

	public boolean isNewLine() {
		return token.equals("\n");
	}

	public boolean isWhiteSpace() {
		return Character.isWhitespace(token.charAt(0));
	}

	public boolean isPreprocessor() {
		return token.startsWith("#");
	}

	public boolean isComment() {
		return token.startsWith("/*") ||
    			token.startsWith("//");
	}

	public boolean isSemicolon() {
		return token.equals(";");
	}

	public boolean isLeftBracket() {
		return token.equals("(") || token.equals("<");
	}

	public boolean isRightBracket() {
		return  token.equals(")") || token.equals(">") || token.equals("*>");
	}

	public boolean isLeftParentheses() {
		return token.equals("(");
	}

	public boolean isLeftBrace() {
		return token.equals("{");
	}

	public boolean isRightBrace() {
		return token.equals("}");
	}

	public boolean isTypeDef() {
		return token.equals("typedef");
	}

	public boolean isAssignment() {
		return token.equals("=");
	}

	public static Token token(String string, int offset) {
		return new Token(string, offset);
	}

	public static Token token(String string) {
		return new Token(string, UNDEFINED_OFFSET);
	}
	public int getBeginOffset() {
		return offset;
	}

	public int getEndOffset() {
		return offset;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((token == null) ? 0 : token.hashCode());
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
		Token other = (Token) obj;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}


}
