package com.ladinc.checkargos.utilities;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;

import com.ladinc.checkargos.utilities.HttpClientHelper;

import android.app.Activity;
import android.util.Log;


public class HtmlCode {
	
	private static final String TAG = "HtmlCode";
	private static final String NO_INTERNET = "No Internet Connection";

	static HttpClient httpclient = HttpClientHelper.getHttpClient();


	public static String getHtmlCode(String url, Activity activity) throws Exception {
		Log.d(TAG, "Fetching Html code for the following url: " + url);

		String htmlCode = "";
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		htmlCode = httpclient.execute(httpget, responseHandler);

		if (!InternetChecker.isOnline(activity)) {
			Log.d(TAG, "No internet");
			throw new Exception(NO_INTERNET);
		}

		return htmlCode;
	}
	
	
	public static String getHtmlCodeNewClient(String url, Activity activity) throws Exception {
		Log.d(TAG, "Fetching Html code for the following url: " + url);

		HttpClient httpclienNew = HttpClientHelper.createHttpClient();
		
		String htmlCode = "";
		HttpGet httpget = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		htmlCode = httpclienNew.execute(httpget, responseHandler);

		if (!InternetChecker.isOnline(activity)) {
			Log.d(TAG, "No internet");
			throw new Exception(NO_INTERNET);
		}

		return htmlCode;
	}

}
