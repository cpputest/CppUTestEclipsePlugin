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
			yieldLanguageUnits.yield(signatureBuilder.build());
		case NEW_FUNCTION:
			signatureBuilder = new SignatureBuilder();
			signatureBuilder.withReturnType(part.toString());
			break;
		case ADD_TO_FUNCTION_NAME:
			signatureBuilder.addToFunctionName(part.toString());
			break;
		case END_OF_FUNCTION:
		case PARAMETER:
		case PART_OF_LONG_FUNCTION_NAME:
		case TOKEN:
			break;
		}
		
	}

	public void read(String sourceCode) {
		CTokenTranslator trans = new CTokenTranslator(this);
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.generateTokensFromSourceCode(sourceCode, trans);
	}

}
