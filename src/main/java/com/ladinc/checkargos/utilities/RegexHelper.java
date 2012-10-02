package com.ladinc.checkargos.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
	
	public static boolean isPatternInText(String text, String pattern)
	{
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(text);

		return m.find(); 
	}
	
	public static String getStringFromText(String text, String pattern)
	{
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(text);

		if (m.find())
		{
			return m.group(1);
		}
		else
		{
			return "";
		}
	}

}
