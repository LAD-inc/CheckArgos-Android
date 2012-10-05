package com.ladinc.checkargos.domain;

import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.ladinc.checkargos.utilities.HtmlCode;
import com.ladinc.checkargos.constants.UrlConstants;

public class Product {
	
	
	private static final String TAG = "Product";
	
	String id;
	String name;
	String price;
	String imageUrl;
	Map<String, String> stockLevels;
	
	public Product(String productId) 
	{
		this.id = productId;
	}

	
	public void getProductInfo(Activity activity) throws Exception
	{
		if (this.id != null)
		{
			String htmlCode = HtmlCode.getHtmlCode(getProductInfoUrl(), activity);
			//Maybe add error param in Json?
			if (htmlCode != "")
			{
				JSONObject jsonObj = new JSONObject(htmlCode);
				this.name = jsonObj.getString("name");
				this.price = jsonObj.getString("price");
				this.imageUrl = jsonObj.getString("imageUrl");
				
				Log.d(TAG, "Product Name: " + this.name);
				Log.d(TAG, "Product Price: " + this.price);
				Log.d(TAG, "Image URL: " + this.imageUrl);
			}
			
		}
	}
	
	private String getProductInfoUrl()
	{
		String url = UrlConstants.serviceUrl + "?function=info&productId=" + this.id;
		return url;
	}

}
