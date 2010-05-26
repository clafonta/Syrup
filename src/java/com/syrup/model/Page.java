package com.syrup.model;

import java.util.List;

import com.syrup.OrderedMap;

public class Page implements PersistableItem {

	private Long id;
	private String name;
    private OrderedMap<Asset> assets = new OrderedMap<Asset>();
	@Override
	public Long getId() {
		
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
		
	}

	public List<Asset> getAssets() {
		
		return this.assets.getOrderedList();
	}
	
	public void deleteAsset(Asset asset){
		if(asset!=null && asset.getId()!=null){
			this.assets.remove(asset.getId());
		}
	}
	public Asset saveOrUpdateAsset(Asset asset){
		return (Asset)this.assets.save(asset);
	}

	public Asset getAssetById(Long id) {
		
		return (Asset)this.assets.get(id);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public int getNextAvailableAssetId(){
		int counter = 0;
		for(Asset asset: getAssets()){
			if(asset.getId()>counter){
				counter = (int) (asset.getId().longValue() + 1);
			}
		}
		return counter;
	}

}
