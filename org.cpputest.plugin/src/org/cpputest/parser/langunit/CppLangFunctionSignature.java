package org.cpputest.parser.langunit;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.cpputest.codeGenerator.CppCode;
import org.cpputest.parser.impl.Token;

public final class CppLangFunctionSignature implements LanguageUnit {

	private String functionName;
	private String returnType;
	private List<String> parameters = new ArrayList<String>();
	private int beginOffset;
	private int endOffset;

	protected void setFunctionName(String functionName) {
		this.functionName = functionName;
		
	}
	public String getFunctionName() {
		return functionName;
	}
	protected void setReturnType(String returnType) {
		this.returnType = returnType;
		
	}

	@Override
	public CppCode getCode() {
		return new CppCode(returnType + " " + functionName + "(" + 
				join(parameters, " ") + ")");
	}

	public boolean isVoid() {
		return returnType.equals("void");
	}

	protected void addToParameter(String parameter) {
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

	@Override
	public boolean isOffsetInclusive(int offset) {
		return beginOffset <= offset && endOffset >= offset;
	}

	protected void setBeginAndEndOffsets(int beginOffset, int endOffset) {
		this.beginOffset = beginOffset;
		this.endOffset = endOffset;
		
	}	
	public static class SignatureBuilder {
		CppLangFunctionSignature signature;
		private String currentDecidedReturnType = "";
		private boolean hasReturnType = false;
		private boolean hasFunctionName = false;
		private Queue<String> tokensBeforeFunctionNameDecided = new LinkedList<String>();
		private int beginOffset;
		private int endOffset;
		
		private SignatureBuilder(String possibleReturnType) {
			signature = new CppLangFunctionSignature();
			addToFunctionDeclaration(possibleReturnType);
		}
		public CppLangFunctionSignature build() {
			signature.setBeginAndEndOffsets(beginOffset, endOffset);
			return signature;
		}
		public SignatureBuilder withBeginOffset(int beginOffset) {
			this.beginOffset = beginOffset;
			return this;
		}
		public SignatureBuilder withEndOffset(int endOffset){
			this.endOffset = endOffset;
			return this;
		}
		public SignatureBuilder addToFunctionDeclaration(String partOfFunctionDeclaration) {
			tokensBeforeFunctionNameDecided.add(partOfFunctionDeclaration);
			addToReturnTypeAndFunctionName();
			return this;
		}
		public SignatureBuilder addToFunctionDeclaration(Token token2) {
			return this;
		}
		private void addToReturnTypeAndFunctionName() {
			while(!tryToFitTokensInTheQueueIntoReturnTypeAndFunctionName())
				tokensBeforeFunctionNameDecided.remove();
		}
		private void reset() {
			hasReturnType = false;
			hasFunctionName = false;
		}
		private boolean tryToFitTokensInTheQueueIntoReturnTypeAndFunctionName() {
			reset();
			for (String token: tokensBeforeFunctionNameDecided)
				if (!addTokenToCurrentTryOfReturnTyeAndFunctionName(token))
					return false;
			return true;
		}
		private boolean addTokenToCurrentTryOfReturnTyeAndFunctionName(
				String partOfFunctionDeclaration) {
			if (!hasReturnType) {
				this.currentDecidedReturnType  = partOfFunctionDeclaration;
				signature.setReturnType(currentDecidedReturnType);
				hasReturnType = true;
			}
			else if (currentDecidedReturnType.equals("const"))
				addToReturnType(partOfFunctionDeclaration);
			else if (hasFunctionName) {
				addToReturnType(signature.getFunctionName());
				signature.setFunctionName(partOfFunctionDeclaration);
			}
			else if (partOfFunctionDeclaration.equals("*"))
				addToReturnType(partOfFunctionDeclaration);
			else {
				signature.setFunctionName(partOfFunctionDeclaration);
				hasFunctionName = true;
			}
			return true;
		}
		public SignatureBuilder addToParameter(String parameter) {
			signature.addToParameter(parameter);
			return this;
		}
		private void addToReturnType(String string) {
			currentDecidedReturnType += " " + string;
			signature.setReturnType(currentDecidedReturnType);
		}
		public boolean isComplete() {
			return hasFunctionName ;
		}
	}
	public static SignatureBuilder builderStartWith(String string) {
		return new SignatureBuilder(string);
	}
}

