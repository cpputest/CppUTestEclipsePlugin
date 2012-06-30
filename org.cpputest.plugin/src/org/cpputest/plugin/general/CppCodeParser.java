package org.cpputest.plugin.general;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CppCodeParser implements ICppCodeParser {
	public Pattern pattern = Pattern.compile("(\\w+)\\s*(&?).*");

	public CppCodeParser() {
	}

	public List<CppFunction> parseFunctions(String string) {
		List<CppFunction> funList = new ArrayList<CppFunction>();
		for (String funString: string.split(";")) {
			Matcher matcher = pattern.matcher(funString);
			if (matcher.find()) {
				String type = matcher.group(1);
				String reference = matcher.group(2);
				funList.add(new CppFunction(funString, type, reference.equals("&")));
			}
		}
		return funList;
	}
}