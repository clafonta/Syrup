package com.syrup.model;

public class Asset implements PersistableItem {

	private Long id;
	private String source = null;
	private int anchorPosX = 0;
	private int anchorPosY = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAnchorPosX(int anchorPosX) {
		this.anchorPosX = anchorPosX;
	}

	public int getAnchorPosX() {
		return anchorPosX;
	}

	public void setAnchorPosY(int anchorPosY) {
		this.anchorPosY = anchorPosY;
	}

	public int getAnchorPosY() {
		return anchorPosY;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}
}
