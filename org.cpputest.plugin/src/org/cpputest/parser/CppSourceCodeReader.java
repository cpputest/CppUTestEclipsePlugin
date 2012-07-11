package org.cpputest.parser;

import java.util.Iterator;
import org.cpputest.parser.impl.CppLikeCodeTokenSplitter;
import org.cpputest.parser.impl.CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator;
import org.cpputest.parser.impl.CppTokensToPotentialLanguagePartsTranslator;
import org.cpputest.parser.impl.Token;
import org.cpputest.parser.langunit.CppLangFunctionSignature;
import org.cpputest.parser.langunit.LanguageUnit;
import org.cpputest.parser.parts.impl.parts.CppPart;

public class CppSourceCodeReader implements SourceCodeReader {
	public class SignatureIterable implements Iterable<CppLangFunctionSignature> {

		private final Iterator<LanguageUnit> units;

		public SignatureIterable(Iterable<LanguageUnit> units) {
			this.units = units.iterator();
		}

		@Override
		public Iterator<CppLangFunctionSignature> iterator() {
			return new Iterator<CppLangFunctionSignature>() {

				@Override
				public boolean hasNext() {
					return units.hasNext();
				}

				@Override
				public CppLangFunctionSignature next() {
					return (CppLangFunctionSignature)units.next();
				}

				@Override
				public void remove() {
				}
				
			};
		}

	}
	@Override
	public Iterable<CppLangFunctionSignature> signatures(String sourceCode) {
		Iterable<Token> tokens = new CppLikeCodeTokenSplitter().tokens(sourceCode);
		Iterable<CppPart> parts = new CppTokensToPotentialLanguagePartsTranslator().getPotentialLanguageParts(tokens);
		Iterable<LanguageUnit> units = new CppPotentialLanguagePartsToMeaningfulLanguageUnitsTranslator().languageUnits(parts);
		return new SignatureIterable(units);
	}

}
