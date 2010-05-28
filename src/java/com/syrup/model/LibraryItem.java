package com.syrup.model;

public class LibraryItem implements PersistableItem {

	private Long id;
	private String path;
	private String name;
	private String groupName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupName() {
		return groupName;
	}
	 
}
