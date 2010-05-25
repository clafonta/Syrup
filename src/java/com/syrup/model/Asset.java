package com.syrup.model;

public class Asset implements PersistableItem {

	private Long id;
	private String source = null;
	private float top = 0;
	private float left = 0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setTop(float top) {
		this.top = top;
	}

	public float getTop() {
		return top;
	}

	public void setLeft(float left) {
		this.left = left;
	}

	public float getLeft() {
		return left;
	}
}
