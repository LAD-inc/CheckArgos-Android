package com.ladinc.checkargos.domain;

public class Store {
	
	String storeName;
	String storeId;
	
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
