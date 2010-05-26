package com.syrup.storage;

import com.syrup.model.Project;

class StorageWriter {

	String StorageAsString(IStorage storage) {
		StringBuffer sb = new StringBuffer();
		sb.append(storage.toString());
		for (Project item : storage.getProjects()) {
			sb.append("Project ID: ").append(item.getId()).append("\n");
			sb.append("Project name: ").append(item.getName()).append(
					"\n");

		}
		return sb.toString();
	}
}
