package com.ladinc.checkargos.utilities;

import org.apache.http.client.HttpClient;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

public class HttpClientHelper {

	
	private static HttpClient httpclient;
	
	
	public static HttpClient getHttpClient()
	{
		if(HttpClientHelper.httpclient == null)
		{
			HttpClientHelper.httpclient = createHttpClient();
		}
		
		return httpclient;
	}
	
	public static HttpClient createHttpClient()
	{
		// Set your params (stopping the redirect to read the headers)
		HttpParams params = new BasicHttpParams();
		params.setParameter("http.connection-manager.timeout", (long)20000);
		params.setParameter("http.socket.timeout",(int)20000);
		params.setParameter("http.connection.timeout",(int)20000);
		HttpClientParams.setRedirecting(params, true);
		
		HttpClient newHttpclient = new DefaultHttpClient(params);
		
		return newHttpclient;
	}
}
