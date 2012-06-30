package org.cpputest.plugin.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CppCodeParser {
	public Pattern pattern = Pattern.compile("(\\w+)\\s*(&?).*");

	public CppCodeParser() {
	}

	public CppFunction parseFunction(String string) {
		Matcher matcher = pattern.matcher(string);
		matcher.find();
		String type = matcher.group(1);
		String reference = matcher.group(2);
		return new CppFunction(type, reference.equals("&"));
	}
}