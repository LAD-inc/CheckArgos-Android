package com.ladinc.checkargos.domain;

public class Store {
	
	public String storeName;
	public String storeId;
	
	public Store(String name, String id)
	{
		this.storeName = name;
		this.storeId = id;
	}

	@Override
	public String toString() {
		return "Store [storeName=" + storeName + ", storeId=" + storeId + "]";
	}
	
	

}
