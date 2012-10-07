package com.ladinc.checkargos.adapters;

import java.util.ArrayList;

import com.ladinc.checkargos.R;
import com.ladinc.checkargos.domain.StockStatus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StockStatusArrayAdapter extends ArrayAdapter<StockStatus>
{
	private ArrayList<StockStatus> stockStatusList;
	
    public StockStatusArrayAdapter(Context context, int textViewResourceId, ArrayList<StockStatus> items) 
    {
        super(context, textViewResourceId, items);
        this.stockStatusList = items;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) 
    {
            View v = convertView;
            if (v == null) 
            {
                LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.stock_row_layout, null);
            }
            StockStatus stockStatus = this.stockStatusList.get(position);
            if (stockStatus != null) 
            {
                    TextView storeName = (TextView) v.findViewById(R.id.storeName);
                    TextView stockLevel = (TextView) v.findViewById(R.id.stockStatus);
                    if (storeName != null) 
                    {
                    	storeName.setText(stockStatus.storeName);                            }
                    if(stockLevel != null)
                    {
                    	stockLevel.setText(stockStatus.stockStatus);
                    }
            }
            return v;
    }
}
