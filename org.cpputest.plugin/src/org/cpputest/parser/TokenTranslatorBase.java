package org.cpputest.parser;

import org.cpputest.parser.parts.CppPart;

public class TokenTranslatorBase implements YieldToken {
	private int current_line = 0;
	private YieldParser yieldParser;
    public TokenTranslatorBase(YieldParser yieldParser){
    	this.yieldParser = yieldParser;
    }
    public int getCurrentLine(){
        return current_line;
    }
	private CppPart read_token(String token){
        if (Character.isWhitespace(token.charAt(0)))
            return null;//sUniversalCodeCounter.NEW_LINE, None
        else
            return input(token);
    }
	protected CppPart input(String token) {
		return null;
	}
	@Override
	public void yield(String token, int line) {
        CppPart fun = read_token(token);
        if (fun != null)
        	yieldParser.yield(fun);
	}
}