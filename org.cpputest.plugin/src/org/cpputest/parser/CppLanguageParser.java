package org.cpputest.parser;

import org.cpputest.parser.parts.CppPart;

public class CppLanguageParser extends TokenTranslatorBase{
	public enum State {
		GLOBAL, DEC, NAMESPACE, DEC_TO_IMP, IMP, FUNCTION_NAME
	}
	private State _state=State.GLOBAL;
;
	private int pa_count = 0;
	private int br_count = 0;
    public CppLanguageParser(YieldParser yieldParser){
    	super(yieldParser);
    }
    @Override
	protected CppPart input(String token) {
    	if (token.startsWith("#") ||
    			token.startsWith("/*") ||
    			token.startsWith("//"))
    		return null;
   
		switch(get_state()){
		case GLOBAL:
			return _GLOBAL(token);
		case FUNCTION_NAME:
			return _GLOBAL(token);
		case DEC:
			return _DEC(token);
		case NAMESPACE:
			return _NAMESPACE(token);
		case DEC_TO_IMP:
			return _DEC_TO_IMP(token);
		case IMP:
			return _IMP(token);
		}
		return null;
	}
    
	private CppPart _GLOBAL(String token){
        if (token.equals("(")) {
            pa_count += 1;
            set_state(State.DEC);
            return CppPart.PartOfLongFunctionName(token);
        }
        else if (token.equals("::")) 
        	set_state(State.NAMESPACE);
        else if (token.equals(";")) {
        	set_state(State.GLOBAL);
        	return CppPart.EndOfGlobalStatement(token);
        }
        else {
        	if (get_state() == State.FUNCTION_NAME)
                return CppPart.AddToFunctionName(token);
        	set_state(State.FUNCTION_NAME);
            return CppPart.StartNewFunction(token, getCurrentLine());
        }
        return null;
    }
    private CppPart _NAMESPACE(String token){
    	set_state(State.GLOBAL);
        return CppPart.AddToFunctionName("::"+token);
    }
    private CppPart _DEC(String token){
        if (token.equals("(") || token.equals("<"))
            pa_count += 1;
        else if(token.equals(")") || token.equals(">") || token.equals("*>")){
            pa_count -= 1;
            if (pa_count == 0) {
            	set_state(State.DEC_TO_IMP);
                return CppPart.EndOfFunctionSignature(token);
            }
        }
        else if (pa_count == 1)
            return CppPart.Parameter(token);
        return CppPart.PartOfLongFunctionName(" " + token);
    }
    private CppPart _DEC_TO_IMP(String token){
        if (token.equals("const"))
            return CppPart.PartOfLongFunctionName(" " + token);
        else if (token.equals("{")){
            br_count += 1;
            set_state(State.IMP);
        }
        else
        	set_state(State.GLOBAL);
        return null;
    }
    private CppPart  _IMP(String token){
        if (token.equals("{"))
            br_count += 1;
        else if(token.equals("}")){
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
