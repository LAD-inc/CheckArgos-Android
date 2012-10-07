package com.ladinc.checkargos.activitys;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import com.ladinc.checkargos.R;
import com.ladinc.checkargos.adapters.StockStatusArrayAdapter;
import com.ladinc.checkargos.domain.Product;
import com.ladinc.checkargos.domain.StockStatus;
import com.ladinc.checkargos.domain.StoreCollection;
import com.ladinc.checkargos.utilities.LoadingDialog;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class StockSearchActivity extends ListActivity implements OnClickListener
{
	private static final String TAG = "StockSearchActivity";
	
	private Button searchStockButton;
	private EditText productIdText;
	
	private Product product;
	private StoreCollection stores;
	
	LoadingDialog loadingDialog;
	
	private ImageView productImage;
	private TextView productInfo;
	
	private ArrayList<StockStatus> stockStatus;
	private StockStatusArrayAdapter statusAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.stock_search);
		
		this.stores = new StoreCollection();
		this.setupViews();
		this.setupListeners();
		
		this.stockStatus = new ArrayList<StockStatus>();
		
		this.statusAdapter = new StockStatusArrayAdapter(this, R.layout.stock_row_layout, this.stockStatus);
		setListAdapter(this.statusAdapter);
		
		new getStoreList().execute("");
		
	}
	
	private void setupViews() 
	{
		this.searchStockButton = (Button) this.findViewById(R.id.checkStockButton);
		this.productIdText = (EditText) this.findViewById(R.id.productIdText);
		
		this.productImage = (ImageView) findViewById(R.id.productImage);
		this.productInfo = (TextView) findViewById(R.id.productName);
		
		loadingDialog = new LoadingDialog(this, "Loading");
	}
	
	private void setupListeners() 
	{	
		this.searchStockButton.setOnClickListener(this);
	}
	
	private boolean validateProductId(String productId)
	{
		if (productId.length() != 7)
		{
			this.productIdText.setError("Product ID must be 7 characters.");
			return false;
		}
		
		return true;
	}

	public void productDidNotExist()
	{
		this.productIdText.setError("No data returned for product ID.");
	}
	


	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.checkStockButton:
				String productId = this.productIdText.getText().toString();
				Log.d(TAG, "User has clicked search with the productID: " + productId);
				clearPrevious();
				
				if  (validateProductId(productId)) 
				{
					new getProductInfo().execute(productId);
				}
				break;
		}
		
	}
	
	public void clearPrevious()
	{
		this.productImage.setImageBitmap(null); 
		
		if (this.stockStatus.size() > 0)
		{
			this.stockStatus = new ArrayList<StockStatus>();
			this.statusAdapter.notifyDataSetChanged();
			
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
		
		for (Map.Entry<String, String> entry : this.stores.irishStores.entrySet()) 
		{
			new getStock().execute(entry.getKey());
		    
		}
	}
	
	
	public void getStockLevel(String storeId) throws Exception
	{
		this.product.getStockforSingleStore(this, storeId);
		//Log.d(TAG, "Stock status : " + this.product. );
	}
	
	public void printProductObject()
	{
		
		this.stockStatus = getStockStatusList();
		
		updateStockList();
		Log.d(TAG, "Product : " + this.product.toString() );
		
	}
	
	public ArrayList<StockStatus> getStockStatusList()
	{
		Map<String, String> stockLevelList = this.product.getStockLevels();
		String storeName;
		String stockLevel;
		StockStatus ss;
		
		ArrayList<StockStatus> ssList = new ArrayList<StockStatus>();
		for (Map.Entry<String, String> entry : stockLevelList.entrySet()) 
		{
			storeName = this.stores.irishStores.get(entry.getKey());
			stockLevel = entry.getValue();
			ss = new StockStatus(storeName, stockLevel);
			ssList.add(ss);
		}
		
		return ssList;
		
	}
	

	public void setProductImage (String url) throws MalformedURLException, IOException
	{
		Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
		this.productImage.setImageBitmap(bitmap); 
	}
	
	public void setProductNameAndPrice(String name, String price)
	{
		
		this.productInfo.setText(name + " - €" +price);
	}
	
	public void displayProductInfo() throws MalformedURLException, IOException
	{
		setProductNameAndPrice(this.product.getName(), this.product.getPrice());
		setProductImage(this.product.getImageUrl());
	}
	
	private void updateStockList() 
	{
		if (this.stockStatus != null || this.stockStatus.size() > 0)
		{
			this.statusAdapter.notifyDataSetChanged();
			this.statusAdapter.clear();
			for(StockStatus ss : this.stockStatus)
			{
				this.statusAdapter.add(ss);
			}
		}
		this.statusAdapter.notifyDataSetChanged();
		
	}
	
	private void displayStoreError() {
		

		Intent intent = new Intent(this, DisplayErrorActivity.class);
		ArrayList<String> errors = new ArrayList<String>();
		errors.add("Error Getting Store List, press ok to retry");
	    intent.putStringArrayListExtra("errorList", errors);
	    intent.putExtra("title", "Checkargos Error Message");
	    	
	    startActivityForResult(intent, 2);
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == 2) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	new getStoreList().execute("");
	        }
	    }
	}

	
	//-------------------------------
	// Sub Class: Get Store List
	// Description: Gets List of stores in background
	//-------------------------------
	
	class getStoreList extends AsyncTask<String, Integer, String> 
	{
		@Override
		protected void onPreExecute() 
		{
			loadingDialog.setMessage("Loading Stores");
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(String... stringArray) {

			try 
			{
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
			if (result == null || result == "") 
			{
				try
				{
					loadingDialog.hideLoadingDialog();
				}
				catch (Exception e)
				{
					//Nothing
				}	
			} 
			else 
			{
				try
				{
					loadingDialog.hideLoadingDialog();
				}
				catch (Exception e)
				{
					//Nothing
				}
				displayStoreError();

			}

		}

	}
	
	//-------------------------------
	// Sub Class: Get Product Info
	// Description: Gets product info in background
	//-------------------------------
	
	class getProductInfo extends AsyncTask<String, Integer, String> 
	{
		@Override
		protected void onPreExecute() 
		{
			loadingDialog.setMessage("Loading Product Info");
			loadingDialog.showLoadingDialog();
		}

		@Override
		protected String doInBackground(String... stringArray) {

			String productId = stringArray[0];

			try 
			{
				createProduct(productId);
				getProductInfo();
				
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
			if (result == null || result == "") 
			{
				try
				{
					loadingDialog.hideLoadingDialog();
				}
				catch (Exception e)
				{
					//Nothing
				}
				
				try 
				{
					displayProductInfo();
					getStockAllStores();
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} 
			else 
			{
				try
				{
					loadingDialog.hideLoadingDialog();
				}
				catch (Exception e)
				{
					//Nothing
				}
				productDidNotExist();

			}

		}

	}
	
	//-------------------------------
	// Sub Class: Get Stock
	// Description: Gets Stock level of an item in one store in the background
	//-------------------------------
	
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
			printProductObject();
		}
	
	}

}
