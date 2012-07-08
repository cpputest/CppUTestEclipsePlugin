package org.cpputest.parser;

import java.util.ArrayList;
import java.util.Iterator;

import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.parser.langunit.LanguageUnit;

public class SignatureIterable implements Iterable<CppLangFunctionSignature> {

	private final String sourceCode;

	public SignatureIterable(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Override
	public Iterator<CppLangFunctionSignature> iterator() {
		final ArrayList<CppLangFunctionSignature> list = new ArrayList<CppLangFunctionSignature>();
		YieldLanguageUnits yp = new YieldLanguageUnits() {
			@Override
			public void yield(LanguageUnit unit) {
				list.add((CppLangFunctionSignature)unit);
			}
		};
		
		CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator reader = new CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator(yp);
		reader.read(sourceCode);
		
		return list.iterator();
		
	}

}
