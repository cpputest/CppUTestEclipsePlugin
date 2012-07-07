package org.cpputest.parser.langunit;

import java.util.ArrayList;
import java.util.List;

import org.cpputest.parser.CppCode;

public class CppLangFunctionSignature implements LanguageUnit {

	private final String currentFun;
	private String functionName;
	private String returnType;
	private List<String> parameters = new ArrayList<String>();

	public CppLangFunctionSignature(String currentFun) {
		this.currentFun = currentFun;
	}

	@Override
	public String toString() {
		return currentFun;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
		
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
		
	}

	@Override
	public CppCode getCode() {
		// TODO Auto-generated method stub
		return new CppCode(returnType + " " + functionName + "(" + 
				join(parameters, " ") + ")");
	}

	public boolean isVoid() {
		return returnType.equals("void");
	}

	public void addToParameter(String parameter) {
		this.parameters.add(parameter);
		
	}
	static public String join(List<String> list, String conjunction)
	{
	   StringBuilder sb = new StringBuilder();
	   boolean first = true;
	   for (String item : list)
	   {
	      if (first)
	         first = false;
	      else
	         sb.append(conjunction);
	      sb.append(item);
	   }
	   return sb.toString();
	}	

}
