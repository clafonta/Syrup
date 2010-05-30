package com.syrup.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.syrup.OrderedMap;

public class Project implements PersistableItem {

	private Long id;
	private OrderedMap<Page> pages = new OrderedMap<Page>();
	// History of pages.
	// Page ID is the key.
	// List of past Page snapshots
	private Map<Long, List<Page>> historyPages = new HashMap<Long, List<Page>>();
	private String name;

	/**
	 * Delete all history for a page. Used when a Page is deleted.
	 * 
	 * @param pageId
	 */
	public void deletePageHistory(Long pageId) {
		this.historyPages.remove(pageId);
	}

	/**
	 * Delete a specific history for a Page
	 * @param pageId
	 * @param version
	 */
	public void deletePageHistoryVersion(Long pageId, String version) {
		int index = -1;
		int count = -1;
		for (Page page : this.historyPages.get(pageId)) {
			count++;
			if (page.getVersion() != null
					&& page.getVersion().trim().equalsIgnoreCase(version)) {
				index = count;
				break;
			}
		}
		if (index > -1) {
			this.historyPages.get(pageId).remove(index);
		}
	}
	
	public List<Page> getPageHistory(Long pageId){
		return this.historyPages.get(pageId);
	}
	
	public Page getPageHistoryByVersion(Long pageId, String version){
		Page pageVersion = null;
		for(Page page: this.historyPages.get(pageId)){
			if(version!=null && version.equalsIgnoreCase(page.getVersion())){
				pageVersion = page;
				break;
			}
		}
		return pageVersion;
	}
	/**
	 * Adds a page to the history, associated to a page ID
	 * @param pageId
	 * @param snapshot
	 */
	public void addPageHistory(Long pageId, Page snapshot){
		List<Page> list = this.historyPages.get(pageId);
		if(list==null){
			list = new ArrayList<Page>();
		}
		list.add(snapshot);
		this.historyPages.put(pageId, list);
	}

	/**
	 * Deletes the Page from this project, including history
	 * 
	 * @param page
	 * @see #deletePageHistory(Long)
	 */
	public void deletePage(Page page) {
		if (page != null) {
			this.pages.remove(page.getId());
			this.deletePageHistory(page.getId());
		}
	}

	/**
	 * 
	 * @param page
	 * @return Page object with ID set.
	 */
	public Page saveOrUpdatePage(Page page) {
		return (Page) this.pages.save(page);
	}

	public List<Page> getPages() {
		return this.pages.getOrderedList();
	}

	public Page getPageById(Long id) {
		return (Page) this.pages.get(id);
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
