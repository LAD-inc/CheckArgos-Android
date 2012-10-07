package com.ladinc.checkargos.activitys;

import java.util.ArrayList;

import com.ladinc.checkargos.R;

import com.ladinc.checkargos.adapters.StockStatusArrayAdapter;
import com.ladinc.checkargos.domain.StockStatus;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class StockStatusListActivity extends ListActivity 
{
	private ArrayList<StockStatus> stockStatus;
	private StockStatusArrayAdapter statusAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_list);
        populateStockStatus();
        
        updateList();
    }

	private void updateList() 
	{
		if (this.stockStatus != null || this.stockStatus.size() > 0)
		{
			this.statusAdapter.notifyDataSetChanged();
			for(StockStatus ss : this.stockStatus)
			{
				this.statusAdapter.add(ss);
			}
		}
		this.statusAdapter.notifyDataSetChanged();
		
	}

	private void populateStockStatus() 
	{
		this.stockStatus = new ArrayList<StockStatus>();
		
		StockStatus one = new StockStatus("Athlone", "1 in stock");
		StockStatus two = new StockStatus("Galway", "5 in stock");
		
		this.stockStatus.add(one);
		this.stockStatus.add(two);
		
	}
    
    
	

}
