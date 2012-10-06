package com.ladinc.checkargos.activitys;

import com.ladinc.checkargos.R;
import com.ladinc.checkargos.domain.Product;
import com.ladinc.checkargos.domain.Store;
import com.ladinc.checkargos.domain.StoreCollection;
import com.ladinc.checkargos.utilities.LoadingDialog;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StockSearchActivity extends Activity implements OnClickListener
{
	private static final String TAG = "StockSearchActivity";
	
	private Button searchStockButton;
	private EditText productIdText;
	
	private Product product;
	private StoreCollection stores;
	
	LoadingDialog loadingDialog;
	

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.stock_search);
		
		this.stores = new StoreCollection();
		this.setupViews();
		this.setupListeners();
		
	}
	
	private void setupViews() 
	{
		this.searchStockButton = (Button) this.findViewById(R.id.checkStockButton);
		this.productIdText = (EditText) this.findViewById(R.id.productIdText);
		
		loadingDialog = new LoadingDialog(this, "Loading");
	}
	
	private void setupListeners() 
	{	
		this.searchStockButton.setOnClickListener(this);
	}



	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.checkStockButton:
				String productId = this.productIdText.getText().toString();
				Log.d(TAG, "User has clicked search with the productID: " + productId);
				new getProductInfo().execute(productId);
				break;
		}
		
	}
	
	public void createProduct(String productId)
	{
		this.product = new Product(productId);
	}
	
	public void getProductInfo() throws Exception
	{
		this.product.getProductInfo(this);
	}
	
	public void getStores() throws Exception
	{
		this.stores.populateIrishStoresFromWeb(this);
	}
	
	public void getStockAllStores() throws Exception
	{
		this.product.clearStockStatus();
		for (Store store : this.stores.irishStores)
		{
			new getStock().execute(store.storeId);
		}
	}
	
	
	public void getStockLevel(String storeId) throws Exception
	{
		this.product.getStockforSingleStore(this, storeId);
		//Log.d(TAG, "Stock status : " + this.product. );
	}
	
	

	
	
	//Sub Class to do work in the background
	class getProductInfo extends AsyncTask<String, Integer, String> 
	{
		@Override
		protected void onPreExecute() 
		{
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(String... stringArray) {

			String productId = stringArray[0];

			try 
			{
				createProduct(productId);
				getProductInfo();
				
				//DONT KEEP HERE
				getStores();
				
				return "";
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return "Error";

		}

		@Override
		protected void onPostExecute(String result) 
		{
			// Check if there was an error
			if (result == null) 
			{
				loadingDialog.hideLoadingDialog();

			} 
			else 
			{
				loadingDialog.hideLoadingDialog();

			}
			
			try 
			{
				getStockAllStores();
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
	
	//Sub Class to do work in the background
		class getStock extends AsyncTask<String, Integer, String> 
		{
			@Override
			protected void onPreExecute() 
			{
				//Do not want loading dialog here
			}

			@Override
			protected String doInBackground(String... stringArray) {

				String storeId = stringArray[0];
				
				try 
				{
					
					getStockLevel(storeId);
					getProductInfo();
					
					//DONT KEEP HERE
					getStores();
					
					return "";
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				return "Error";

			}

			@Override
			protected void onPostExecute(String result) 
			{

			}

		}

}