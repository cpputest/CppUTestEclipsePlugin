package org.cpputest.parser;

import org.cpputest.parser.langunit.SignatureBuilder;
import org.cpputest.parser.parts.CppPart;

public class CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator implements YieldParser {
	private final YieldLanguageUnits yieldLanguageUnits;
	private SignatureBuilder signatureBuilder = new SignatureBuilder("");
	public CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator(YieldLanguageUnits yp) {
		yieldLanguageUnits = yp;
	}

	@Override
	public void yield(CppPart part) {
		switch(part.getType()){
		case END_OF_FUNCTION_SIGNATURE:
			if (signatureBuilder.isComplete())
				yieldLanguageUnits.yield(signatureBuilder.build());
		case MAYBE_NEW_FUNCTION:
			signatureBuilder = new SignatureBuilder(part.toString());
			break;
		case MAYBE_PART_OF_FUNCTION_NAME:
			signatureBuilder.addToFunctionDeclaration(part.toString());
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
		CppTokensToPotentialLanguagePartsTranslator trans = new CppTokensToPotentialLanguagePartsTranslator(this);
		CppLikeCodeTokenSplitter tokenizer = new CppLikeCodeTokenSplitter();
		tokenizer.generateTokensFromSourceCode(sourceCode, trans);
	}

}
