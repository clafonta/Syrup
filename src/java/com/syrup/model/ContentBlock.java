package com.syrup.model;

import java.util.Date;

/**
 * 
 * @author chadlafontaine
 * 
 */
public class ContentBlock implements PersistableItem {

	private Long id;
	private String author;
	private Date timestamp;
	private String content;

	@Override
	public Long getId() {

		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	

}
