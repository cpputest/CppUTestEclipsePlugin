package org.cpputest.parser;

import org.cpputest.parser.langunit.SignatureBuilder;
import org.cpputest.parser.parts.CppPart;

public class CppLanguageUnitReader implements YieldParser {
	private final YieldLanguageUnits yieldLanguageUnits;
	private SignatureBuilder signatureBuilder = new SignatureBuilder();
	public CppLanguageUnitReader(YieldLanguageUnits yp) {
		yieldLanguageUnits = yp;
	}

	@Override
	public void yield(CppPart part) {
		switch(part.getType()){
		case END_OF_FUNCTION_SIGNATURE:
			if (signatureBuilder.isComplete())
				yieldLanguageUnits.yield(signatureBuilder.build());
		case NEW_FUNCTION:
			signatureBuilder = new SignatureBuilder();
			signatureBuilder.withReturnType(part.toString());
			break;
		case ADD_TO_FUNCTION_NAME:
			if (part.isStar())
				signatureBuilder.addToReturnType(part.toString());
			else
				signatureBuilder.addToFunctionName(part.toString());
			break;
		case END_OF_FUNCTION:
		case PARAMETER:
			signatureBuilder.addToParameter(part.toString());
		case PART_OF_LONG_FUNCTION_NAME:
		case TOKEN:
			break;
		}
		
	}

	public void read(String sourceCode) {
		CppLanguageParser trans = new CppLanguageParser(this);
		CppLikeCodeTokenSplitter tokenizer = new CppLikeCodeTokenSplitter();
		tokenizer.generateTokensFromSourceCode(sourceCode, trans);
	}

}
