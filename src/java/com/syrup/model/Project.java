package com.syrup.model;

import java.util.List;

import com.syrup.OrderedMap;

public class Project implements PersistableItem {

	private Long id;
	private OrderedMap<Page> pages = new OrderedMap<Page>();
	private String name;
	
	public void deletePage(Page page){
		if(page!=null){
		this.pages.remove(page.getId());
		}
	}
	
	public Page saveOrUpdatePage(Page page){
		return (Page)this.pages.save(page);
	}

	public List<Page> getPages(){
		return this.pages.getOrderedList();
	}
	public Page getPageById(Long id){
		return (Page)this.pages.get(id);
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
