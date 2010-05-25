package com.syrup.model;

import java.util.List;

import com.syrup.OrderedMap;

public class Page implements PersistableItem {

	private Long id;
	private String shortName;
    private OrderedMap<Asset> assets = new OrderedMap<Asset>();
	@Override
	public Long getId() {
		
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return shortName;
	}

	public List<Asset> getAssets() {
		return this.assets.getOrderedList();
	}
	
	public Asset saveOrUpdateAsset(Asset asset){
		return (Asset)this.assets.save(asset);
	}

}
