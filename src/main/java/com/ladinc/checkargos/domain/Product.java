package com.ladinc.checkargos.domain;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;

import com.ladinc.checkargos.utilities.HtmlCode;
import com.ladinc.checkargos.utilities.RegexHelper;

public class Product {
	
	private static String REGEX_PRODUCT_EXIST = "Sorry, we are unable to find the catalogue number(s) highlighted in your list.";
	private static String REGEXT_PAGE_TITLE = "<title>([^`]*?)<\\/title>";
	
	String id;
	String name;
	String price;
	String imageUrl;
	Map<String, String> stockLevels;
	
	public void getProductInfo(Activity activity) throws Exception
	{
		if (id != null)
		{
			String url = getProductUrl();
			String htmlCode = HtmlCode.getHtmlCode(url, activity);
			
			if(doesProductExist(htmlCode))
			{
				
			}
		}
	}
	
	public boolean doesProductExist(String htmlCode)
	{
		return RegexHelper.isPatternInText(htmlCode, REGEX_PRODUCT_EXIST);
	}
	
	public String getProductName(String htmlCode)
	{
		String productPageTitle = RegexHelper.getStringFromText(htmlCode, REGEXT_PAGE_TITLE);
		
		String[] tempArray =  productPageTitle.split("at Argos.ie");
		
		return tempArray[0];
	}
	
	
	public String getProductUrl()
	{
		return "http://www.argos.ie/static/Product/partNumber/" + this.id + ".htm";
	}

}
