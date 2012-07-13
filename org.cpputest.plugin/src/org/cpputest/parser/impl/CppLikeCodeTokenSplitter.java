/*
 * This code is ported from a Python project "hfcca" by me. -- terryyin
 * 
 * */
package org.cpputest.parser.impl;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CppLikeCodeTokenSplitter {
	private Pattern token_pattern = Pattern.compile("(\\n|\\w+|/\\*|//|:=|::|>=|\\*=|>|#\\s*define|#\\s*if|#\\s*else|#\\s*endif|#\\s*\\w+|[!%^&\\*\\-=+\\|\\\\<>/\\]\\+]+|\\.{3}|.)");

	public class TokenIterable implements Iterable<Token> {
		private final Iterator<Token> iterator;
		public TokenIterable(String string) {
			iterator = new TokenIterator(string);
		}

		@Override
		public Iterator<Token> iterator() {
			return iterator;
		}
	}
	public class TokenIterator implements Iterator<Token> {
		
		private Token nextValue;
		private final String sourceCode;
	    private boolean in_middle_of_empty_lines = false;
	    private int currentIndexInCode = 0;
	    private final Matcher m;
		public TokenIterator(String string) {
			this.sourceCode = string;
	    	m = token_pattern.matcher(sourceCode);
		}

		@Override
		public boolean hasNext() {
			if (nextValue == null)
				nextValue = generateTokensFromSourceCode();
			return nextValue != null;
		}

		@Override
		public Token next() {
			Token ret = nextValue;
			nextValue = null;
			return ret;
		}

		@Override
		public void remove() {
		}

		private Token generateTokensFromSourceCode(){
			Token token;
			while (true){
				token = generateTokensFromCodeWithMultipleNewlines();
				if (token == null) break;
		        if (!token.isNewLine() || ! in_middle_of_empty_lines) 
	        		return token;
		        in_middle_of_empty_lines = token.isNewLine();
				
			}
			return null;
		}
		private Token generateTokensFromCodeWithMultipleNewlines(){
		    while (true) {
		    	int currentTokenOffset = currentIndexInCode;
		        if(!m.find(currentIndexInCode))
		            break;
		        String token = m.group(0);
		        if (token.equals("\n")) {
		            currentIndexInCode = currentIndexInCode + 1;
	        		currentTokenOffset += 1;
		        }
		        else if (token.startsWith("#")){
		            while(true){
		                int bindex = currentIndexInCode + 1;
		                currentIndexInCode = sourceCode.indexOf("\n", bindex);;  
		                if (currentIndexInCode == -1)
		                    break;
		                if (!sourceCode.substring(bindex,currentIndexInCode).trim().endsWith("\\"))
		                    break;
		            }
		            if(currentIndexInCode == -1)
		                break;
		            token = sourceCode.substring(m.start(0),currentIndexInCode);
		        }
		        else if (token.equals("/*")) {
		            currentIndexInCode = sourceCode.indexOf("*/", currentIndexInCode+2);
		            if (currentIndexInCode == -1)
		                break;
		            currentIndexInCode += 2;
		            token = sourceCode.substring(m.start(0),currentIndexInCode);
		        }
		        else if (token.equals("//") || token.equals("#if") || token.equals("#endif")) {
		            currentIndexInCode = sourceCode.indexOf("\n", currentIndexInCode);  
		            if (currentIndexInCode == -1)
		                break;
		        }
		        else if(token.equals("\"") || token.equals("\'")) {
		            while(true){
		                currentIndexInCode += 1;
		                currentIndexInCode = sourceCode.indexOf(token, currentIndexInCode); 
		                if (currentIndexInCode == -1)
		                    break;
		                if (sourceCode.charAt(currentIndexInCode - 1) == '\\' && sourceCode.charAt(currentIndexInCode - 2) != '\\')
		                    continue;
		                break;
		            }
		            if (currentIndexInCode == -1)
		                break;
		            token = sourceCode.substring(m.start(0),currentIndexInCode+1);
		            currentIndexInCode = currentIndexInCode + 1;
		        }
		        else
		            currentIndexInCode = m.end(0);
		        if (!Character.isWhitespace(token.charAt(0)) && token.charAt(0) != '#')
		            return new Token(token, currentTokenOffset);
		        
		    }
			return null;
		}
	}
	public TokenIterable tokens(String string) {
		return new TokenIterable(string);
	}
}
