package org.cpputest.parser.langunit;

import java.util.LinkedList;
import java.util.Queue;

public class SignatureBuilder {
	CppLangFunctionSignature signature;
	private String currentDecidedReturnType = "";
	private boolean hasReturnType = false;
	private boolean hasFunctionName = false;
	private Queue<String> tokensBeforeFunctionNameDecided = new LinkedList<String>();
	
	public SignatureBuilder(String possibleReturnType) {
		signature = new CppLangFunctionSignature("");
		addToFunctionDeclaration(possibleReturnType);
		
	}
	public CppLangFunctionSignature build() {
		return signature;
	}
	public SignatureBuilder addToFunctionDeclaration(String partOfFunctionDeclaration) {
		tokensBeforeFunctionNameDecided.add(partOfFunctionDeclaration);
		addToReturnTypeAndFunctionName();
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