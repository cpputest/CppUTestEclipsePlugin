package org.terryyin.codeparser;

import org.terryyin.codeparser.parts.CppAddToFunctionName;
import org.terryyin.codeparser.parts.CppEndOfFunction;
import org.terryyin.codeparser.parts.CppParameter;
import org.terryyin.codeparser.parts.CppPart;
import org.terryyin.codeparser.parts.CppPartOfLongFunctionName;
import org.terryyin.codeparser.parts.CppStartNewFunction;
import org.terryyin.codeparser.parts.CppToken;

public class CTokenTranslator extends TokenTranslatorBase{
	public enum State {
		GLOBAL, DEC, NAMESPACE, DEC_TO_IMP, IMP
	}
	private State _state=State.GLOBAL;
;
	private int pa_count = 0;
	private int br_count = 0;
    public CTokenTranslator(YieldParser yieldParser){
    	super(yieldParser);
    }
    @Override
	protected CppPart input(String token) {
		switch(get_state()){
		case GLOBAL:
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
            return new CppPartOfLongFunctionName(token);
        }
        else if (token.equals("::")) 
        	set_state(State.NAMESPACE);
        else
            return new CppStartNewFunction(token, getCurrentLine());
        return null;
    }
    private CppPart _NAMESPACE(String token){
    	set_state(State.GLOBAL);
        return new CppAddToFunctionName("::"+token);
    }
    private CppPart _DEC(String token){
        if (token.equals("(") || token.equals("<"))
            pa_count += 1;
        else if(token.equals(")") || token.equals(">") || token.equals("*>")){
            pa_count -= 1;
            if (pa_count == 0)
            	set_state(State.DEC_TO_IMP);
        }
        else if (pa_count == 1)
            return new CppParameter(token);
        return new CppPartOfLongFunctionName(" " + token);
    }
    private CppPart _DEC_TO_IMP(String token){
        if (token.equals("const"))
            return new CppPartOfLongFunctionName(" " + token);
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
                return new CppEndOfFunction();
            }
        }
    	return new CppToken(token);
    }
    protected State get_state() {
		return _state;
	}
	protected void set_state(State _state) {
		this._state = _state;
	}
    
}
