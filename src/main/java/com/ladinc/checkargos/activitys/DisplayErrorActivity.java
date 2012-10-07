package com.ladinc.checkargos.activitys;

import java.util.List;

import com.ladinc.checkargos.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class DisplayErrorActivity extends Activity implements OnClickListener {
	
	private TextView errorsText;
	private Button okButton;
	List<String> errorsList;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try
		{
			super.onCreate(savedInstanceState);
			
			Bundle bundle = getIntent().getExtras();
			errorsList = (List<String>) bundle.get("errorList");
			
			String title = bundle.get("title").toString();
			setTitle(title);
			
			setContentView(R.layout.error_popup);
			
			this.setupViews();
			this.setupListeners();
			
			populateErrors();
			
			
		}
		catch (Exception e)
		{
			// this is the line of code that sends a real error message to the log
			Log.e("ERROR", "ERROR IN CODE: " + e.toString());
	 
			// this is the line that prints out the location in
			// the code where the error occurred.
			e.printStackTrace();
		}
	}
	
	private void setupListeners() {
		okButton.setOnClickListener(this);
		
	}

	private void setupViews() {
		errorsText = (TextView) this.findViewById(R.id.errorText);
		okButton = (Button) this.findViewById(R.id.okButton);
	}
	
	private void populateErrors()
	{
		String errorsAsHtml = "";
		String bullet = "&#8226; ";
		String newLine = " <br/> ";
		
		boolean afterFirstLine = false;
		
		for (String error : errorsList)
		{
			if (afterFirstLine)
			{
				errorsAsHtml = errorsAsHtml + newLine;
			}
			errorsAsHtml = errorsAsHtml + bullet + error + newLine;
			
			afterFirstLine = true;
		}
		
		errorsText.setText(Html.fromHtml(errorsAsHtml));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) 
		{
			case R.id.okButton:
				this.finish();
		}
		
	}

	
	

}
