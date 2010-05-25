package com.syrup.storage;

import com.syrup.model.Page;

class StorageWriter {

	String StorageAsString(IStorage storage) {
		StringBuffer sb = new StringBuffer();
		sb.append(storage.toString());
		for (Page item : storage.getPages()) {
			sb.append("Page ID: ").append(item.getId()).append("\n");
			sb.append("Page name: ").append(item.getPageName()).append(
					"\n");

		}
		return sb.toString();
	}
}
