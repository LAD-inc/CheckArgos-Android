package com.ladinc.checkargos.domain;

import java.util.Collections;
import java.util.HashMap;
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
	
	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getPrice() {
		return price;
	}


	public String getImageUrl() {
		return imageUrl;
	}


	public Map<String, String> getStockLevels() {
		return stockLevels;
	}
	
	public Map<String, String> getCopyOfStockLevels(){
		return Collections.unmodifiableMap(this.stockLevels);
	}


	public Product(String productId) 
	{
		this.id = productId;
		this.stockLevels = new HashMap<String, String>();
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
	
	public void clearStockStatus()
	{
		this.stockLevels.clear();
	}
	
	public void getStockforSingleStore(Activity activity, String storeId) throws Exception
	{
		if (this.id != null)
		{
			String htmlCode = HtmlCode.getHtmlCodeNewClient(getSingleStoreStockUrl(storeId), activity);
			//Maybe add error param in Json?
			if (htmlCode != "")
			{
				JSONObject jsonObj = new JSONObject(htmlCode);
				//String storeId = jsonObj.getString("storeId");
				String stockStatus = jsonObj.getString("stock");

				
				Log.d(TAG, "Store: " + storeId + " Stock: " + stockStatus);
				
				this.stockLevels.put(storeId, stockStatus);
			}
			
		}
	}
	
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price
				+ ", imageUrl=" + imageUrl + "]";
	}


	private String getSingleStoreStockUrl(String storeId)
	{
		String url = UrlConstants.serviceUrl + "?function=stock&productId=" + this.id + "&storeId=" + storeId;
		return url;
	}

}
