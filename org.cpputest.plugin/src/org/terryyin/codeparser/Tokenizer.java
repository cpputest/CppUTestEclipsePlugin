/*
 * This code is ported from a Python project "hfcca" by me. -- terryyin
 * 
 * */
package org.terryyin.codeparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
	private Pattern token_pattern = Pattern.compile("(\\w+|/\\*|//|:=|::|>=|\\*=|>|#\\s*define|#\\s*if|#\\s*else|#\\s*endif|#\\s*\\w+|[!%^&\\*\\-=+\\|\\\\<>/\\]\\+]+|.)");

	public void generateTokensFromSourceCode(String source_code, final YieldToken yieldToken){
		YieldToken yt = new YieldToken() {
		    boolean in_middle_of_empty_lines = false;
			public void yield(String token, int line){
		        if (!token.equals("\n") || ! in_middle_of_empty_lines) 
		        		yieldToken.yield(token, line);
	            in_middle_of_empty_lines = token.equals("\n");
			}
		};
		generateTokensFromCodeWithMultipleNewlines(source_code, yt);
	}
	private void generateTokensFromCodeWithMultipleNewlines(String source_code, YieldToken yieldToken){
	    int index = 0;
	    int line = 1;
    	Matcher m = token_pattern.matcher(source_code);
	    while (true) {
	        if(!m.find(index))
	            break;
	        String token = m.group(0);
	        if (token.equals("\n")) 
        		line += 1;
	        
	        if (token.startsWith("#"))
	            token = "#" + token.substring(1).trim();
	            
	        if (token.equals("#define")) {
	            while(true){
	                int bindex = index + 1;
	                index = source_code.indexOf("\n", bindex);;  
	                if (index == -1)
	                    break;
	                if (source_code.substring(bindex,index).trim().endsWith("\\"))
	                    break;
	            }
	            if(index == -1)
	                break;
	            token = source_code.substring(m.start(0),index);
	        }
	        else if (token.equals("/*")) {
	            index = source_code.indexOf("*/", index+2);
	            if (index == -1)
	                break;
	            index += 2;
	            token = source_code.substring(m.start(0),index);
	        }
	        else if (token.equals("//") || token.equals("#if") || token.equals("#endif")) {
	            index = source_code.indexOf("\n", index);  
	            if (index == -1)
	                break;
	        }
	        else if(token.equals("\"") || token.equals("\'")) {
	            while(true){
	                index += 1;
	                index = source_code.indexOf(token, index); 
	                if (index == -1)
	                    break;
	                if (source_code.charAt(index - 1) == '\\' && source_code.charAt(index - 2) != '\\')
	                    continue;
	                break;
	            }
	            if (index == -1)
	                break;
	            token = source_code.substring(m.start(0),index+1);
	            index = index + 1;
	        }
	        else
	            index = m.end(0);
	        line += (token.split("\n").length - 1);
	        if (!Character.isWhitespace(token.charAt(0)) || token.equals("\n"))
	            yieldToken.yield(token, line);
	    }
	}
}
