package com.syrup.model;

import java.util.Date;

/**
 * This represents an 'annotation', containing X, Y (or top,
 * left positions) relative to a canvas, in additions width size,
 * containing text.
 * 
 * @author chadlafontaine
 * 
 */
public class Annotation {

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public Float getTop() {
		return top;
	}
	public void setTop(Float top) {
		this.top = top;
	}
	public Float getLeft() {
		return left;
	}
	public void setLeft(Float left) {
		this.left = left;
	}
	public Float getWidth() {
		return width;
	}
	public void setWidth(Float width) {
		this.width = width;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	private Long id;
	private Long pageId;
	private Float top = new Float(0);
	private Float left = new Float(0);
	private Float width = new Float(0);
	private String text = null;
	private Date timestamp = null;
	private User author = null;

}
