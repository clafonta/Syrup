package com.syrup.model;

import java.util.List;

import com.syrup.OrderedMap;

public class Page implements PersistableItem {

	private Long id;
	private String name;
	private String version = "current";
	private OrderedMap<Asset> assets = new OrderedMap<Asset>();
	private OrderedMap<ContentBlock> contentBlocks = new OrderedMap<ContentBlock>();

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

	public void deleteContentBlock(ContentBlock contentBlock) {
		if (contentBlock != null && contentBlock.getId() != null) {
			this.contentBlocks.remove(contentBlock.getId());
		}
	}

	public void deleteAsset(Asset asset) {
		if (asset != null && asset.getId() != null) {
			this.assets.remove(asset.getId());
		}
	}

	public ContentBlock saveOrUpdateContentBlock(ContentBlock contentBlock) {
		return (ContentBlock) this.contentBlocks.save(contentBlock);
	}

	public Asset saveOrUpdateAsset(Asset asset) {
		return (Asset) this.assets.save(asset);
	}

	public ContentBlock getContentBlockById(Long id){
		return (ContentBlock) this.contentBlocks.get(id);
	}
	
	public Asset getAssetById(Long id) {

		return (Asset) this.assets.get(id);
	}
	
	public void deleteAssets(){
		this.assets = new OrderedMap<Asset>();
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	/**
	 * Helper method. Used to create the counter ID when an image asset is added
	 * to the canvas.
	 * 
	 * @return
	 */
	public int getNextAvailableAssetId() {
		int counter = 0;
		for (Asset asset : getAssets()) {
			if (asset.getId() > counter) {
				counter = (int) (asset.getId().longValue() + 1);
			}
		}
		return counter;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

}
