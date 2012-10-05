package com.ladinc.checkargos.domain;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.ladinc.checkargos.constants.UrlConstants;
import com.ladinc.checkargos.utilities.HtmlCode;

public class StoreCollection 
{
	private static final String TAG = "StoreCollection";
	
	public List<Store> irishStores;

	public void populateIrishStoresFromWeb(Activity activity) throws Exception
	{
		String htmlCode = HtmlCode.getHtmlCode(returnGetStoresUrl(), activity);
		//Maybe add error param in Json?
		if (htmlCode != "")
		{
			this.irishStores = new ArrayList<Store>();
			
			JSONArray entries = new JSONArray(htmlCode);
			
			Store store;
			
			for (int i = 0; i < entries.length(); i++)
			{
				JSONObject storeJson = entries.getJSONObject(i);
				store = new Store(storeJson.getString("name"), storeJson.getString("id"));
				this.irishStores.add(store);
				Log.d(TAG, "Adding Store: " +i + " " + store);
				
			}
			
			Log.d(TAG, "Added "+ this.irishStores.size() + " stores");
			

		}
		
	}
	
	private String returnGetStoresUrl()
	{
		String url = UrlConstants.serviceUrl + "?function=getStores";
		return url;
	}
	
}
