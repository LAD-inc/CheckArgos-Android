package com.ladinc.checkargos.utilities;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {
	
	public static JSONObject getJsonObject(String jsonText) throws JSONException
	{
		JSONObject jsonObj = new JSONObject(jsonText);
		
		return jsonObj;
	}

}
