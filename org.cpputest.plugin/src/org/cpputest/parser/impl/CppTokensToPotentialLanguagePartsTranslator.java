package org.cpputest.parser.impl;

import java.util.Iterator;

import org.cpputest.parser.parts.impl.parts.CppPart;

public class CppTokensToPotentialLanguagePartsTranslator{
	private enum State {
		GLOBAL, DEC, NAMESPACE, DEC_TO_IMP, IMP, FUNCTION_NAME, TYPEDEF, GLOBAL_ASSIGNMENT
	}
    public CppTokensToPotentialLanguagePartsTranslator() {
	}
	public class PotentialLanguagePartsIterator implements Iterator<CppPart> {
		private State _state=State.GLOBAL;

		private int pa_count = 0;
		private int br_count = 0;

		private CppPart nextValue = null;
		private final Iterator<Token> tokens;

		public PotentialLanguagePartsIterator(Iterable<Token> tokens) {
			this.tokens = tokens.iterator();
		}

		@Override
		public boolean hasNext() {
			if (nextValue  == null)
				nextValue = getNextPotentialLanguagePart();
			return nextValue != null;
		}

		@Override
		public CppPart next() {
			CppPart ret = nextValue;
			nextValue = null;
			return ret;
		}

		@Override
		public void remove() {
		}

		private CppPart getNextPotentialLanguagePart() {
			while (tokens.hasNext()){
				Token token = tokens.next();
				CppPart fun = read_token(token);
				if (fun != null)
					return fun;
			}
			return null;
		}
		private CppPart read_token(Token token){
	        if (token.isWhiteSpace())
	            return null;//sUniversalCodeCounter.NEW_LINE, None
	        else
	            return input(token);
	    }
		protected CppPart input(Token token) {
	    	if (token.isPreprocessor() || 
	    			token.isComment())
	    		return null;
	   
			switch(get_state()){
			case GLOBAL:
				return _GLOBAL(token);
			case FUNCTION_NAME:
				return _GLOBAL(token);
			case GLOBAL_ASSIGNMENT:
				return _GLOBAL_ASSIGNMENT(token);
			case DEC:
				return _DEC(token);
			case NAMESPACE:
				return _NAMESPACE(token);
			case DEC_TO_IMP:
				return _DEC_TO_IMP(token);
			case IMP:
				return _IMP(token);
			case TYPEDEF:
				return _TYPEDEF(token);
			}
			return null;
		}
	    
		private CppPart _GLOBAL(Token token){
	        if (token.isAssignment()) {
	        	set_state(State.GLOBAL_ASSIGNMENT);
	            return CppPart.Assignment();
	        }
	        else if (token.isTypeDef()) {
	        	set_state(State.TYPEDEF);
	            return CppPart.Typedef();
	        }
	        else if (token.isLeftParentheses()) {
	            pa_count += 1;
	            set_state(State.DEC);
	            return CppPart.PartOfLongFunctionName(token);
	        }
	        else if (token.equals("::")) 
	        	set_state(State.NAMESPACE);
	        else if (token.isSemicolon()) {
	        	set_state(State.GLOBAL);
	        	return CppPart.EndOfGlobalStatement();
	        }
	        else {
	        	if (get_state() == State.FUNCTION_NAME)
	                return CppPart.AddToFunctionName(token);
	        	set_state(State.FUNCTION_NAME);
	            return CppPart.StartNewFunction(token);
	        }
	        return null;
	    }
		private CppPart _GLOBAL_ASSIGNMENT(Token token) {
	        if (token.isSemicolon()) {
	        	set_state(State.GLOBAL);
	        	return CppPart.EndOfGlobalStatement();
	        }
			return null;
		}
		private CppPart _TYPEDEF(Token token) {
			if (token.isSemicolon())
	        	set_state(State.GLOBAL);
			return null;
		}
	    private CppPart _NAMESPACE(Token token){
	    	set_state(State.GLOBAL);
	        return CppPart.AddToFunctionName(new Token("::"+token.toString(), 0));
	    }
	    private CppPart _DEC(Token token){
	        if (token.isLeftBracket())
	            pa_count += 1;
	        else if(token.isRightBracket()){
	            pa_count -= 1;
	            if (pa_count == 0) {
	            	set_state(State.DEC_TO_IMP);
	                return CppPart.EndOfFunctionSignature(token);
	            }
	        }
	        else if (pa_count == 1)
	            return CppPart.Parameter(token);
	        return CppPart.PartOfLongFunctionName(new Token(" " + token.toString(), 0));
	    }
	    private CppPart _DEC_TO_IMP(Token token){
	        if (token.equals("const"))
	            return CppPart.PartOfLongFunctionName(new Token(" " + token.toString(), 0));
	        else if (token.isLeftBrace()){
	            br_count += 1;
	            set_state(State.IMP);
	        }
	        else
	        	set_state(State.GLOBAL);
	        return null;
	    }
	    private CppPart  _IMP(Token token){
	        if (token.isLeftBrace())
	            br_count += 1;
	        else if(token.isRightBrace()){
	            br_count -= 1;
	            if (br_count == 0){
	            	set_state(State.GLOBAL);
	                return CppPart.EndOfFunction();
	            }
	        }
	    	return CppPart.Token(token);
	    }
	    protected State get_state() {
			return _state;
		}
		protected void set_state(State _state) {
			this._state = _state;
		}
	}
	public Iterable<CppPart> getPotentialLanguageParts(final Iterable<Token> tokens) {
		return new Iterable<CppPart> (){

			@Override
			public Iterator<CppPart> iterator() {
				return new PotentialLanguagePartsIterator(tokens);
			}
			
		};
	}
    
}
