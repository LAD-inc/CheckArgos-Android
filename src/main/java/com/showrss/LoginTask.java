package com.showrss;

//TODO: Fix imports
import java.io.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.util.Log;

public class LoginTask implements Runnable{
	private static final String TAG = "LoginTask";
	private final String userName, password;

	
	
	public LoginTask(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	@Override
	public void run() {
		attemptLogin(userName, password);
		
	}
	
	private boolean attemptLogin (String user, String pass)
	{
		Log.d(TAG, "Attempting to login as: " + user );
		
		
		//Should we throw and exception on failed logins?
		if (!validateUserName(user))
		{
			Log.d(TAG, "Invalid Login name" );
			return false;
		}
		
		//TODO: Get this working
		//String loginURL = getString(R.string.loginURL);
		String loginURL = "http://showrss.karmorra.info/?cs=login";
		
		try {
			
			String charset = "UTF-8";
			
			URL url = new URL(loginURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			//TODO: Do we need to set these? Are they right?
			con.setReadTimeout(10000 /*Milliseconds*/);
			con.setConnectTimeout(15000 /*Milliseconds*/);
			
			con.setRequestMethod("POST");
			
			con.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
			con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			con.setRequestProperty("Accept-Charset", charset);
			con.setRequestProperty("Accept-Encoding", "text/html,application/xhtml+xml,application/xml");
			con.setRequestProperty("Content-Length", "32");
			
			String query = "username=" + URLEncoder.encode(user, charset);
			query = query + "&password=" + URLEncoder.encode(password, "UTF-8");
			
			con.setDoOutput(true);
			
			OutputStream output = null;
			try {
			     output = con.getOutputStream();
			     output.write(query.getBytes(charset));
			} finally {
			     if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
			}
			
			InputStream response = con.getInputStream();
			
			
			
			//TODO: Figure out how to read the response that comes back!
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	

	//ShowRSS rules on valid user names:
	//"The username should be a word without strange symbols, between 2 and 12 characters, and not be in use by another user."
	private boolean validateUserName(String user){
		//"between 2 and 12" sounds like 3-11 but im airing on the side of caution sayings its 2-12
		return (user.length() >= 2 && user.length() <= 12);
	}

}
