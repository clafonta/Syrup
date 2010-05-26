/*
 * Copyright 2002-2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.syrup.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.syrup.OrderedMap;
import com.syrup.model.Project;
import com.syrup.model.PersistableItem;
import com.syrup.storage.xml.XmlFactory;
import com.syrup.ui.StartUpServlet;

/**
 * In memory implementation to the storage of projects.
 * 
 * @author chad.lafontaine
 */
public class InMemoryStorage implements IStorage {

	private OrderedMap<Project> syrupProjectStore = new OrderedMap<Project>();

	private static Logger logger = Logger
			.getLogger(InMemoryStorage.class);

	private static InMemoryStorage store = new InMemoryStorage();

	/**
	 * 
	 * @return
	 */
	static InMemoryStorage getInstance() {
		return store;
	}

	/**
	 * HACK: this class is supposed to be a singleton but making this public for
	 * XML parsing (Digester)
	 * 
	 * Error is:
	 * 
	 * Class org.apache.commons.digester.ObjectCreateRule can not access a
	 * member of class com.syrup.storage.InMemoryStorage with modifiers
	 * "private"
	 * 
	 * Possible Fix: write/implement objectcreatefactory classes.
	 * 
	 * Example:
	 * 
	 * <pre>
	 * http://jsp.codefetch.com/example/fr/storefront-source/com/oreilly/struts/storefront/service/memory/StorefrontMemoryDatabase.java?qy=parse+xml
	 * </pre>
	 */
	public InMemoryStorage() {

	}

	public void deleteEverything() {

		this.syrupProjectStore = new OrderedMap<Project>();
		this.writeMemoryToFile();
	}

	public Project getProjectById(Long id) {
		return syrupProjectStore.get(id);
	}

	public Project saveOrUpdateProject(Project project) {
		PersistableItem item = syrupProjectStore.save(project);
		this.writeMemoryToFile();
		return (Project) item;
	}

	public void deleteProject(Project project) {
		if (project != null) {
			syrupProjectStore.remove(project.getId());
			this.writeMemoryToFile();
		}
	}

	public List<Project> getProjects() {
		return this.syrupProjectStore.getOrderedList();
	}

	/**
	 * Every time something gets saved, we write to memory.
	 */
	private synchronized void writeMemoryToFile() {
		File f = new File(StartUpServlet.APP_DEFINITIONS);
		try {
			FileOutputStream fop = new FileOutputStream(f);
			XmlFactory g = new XmlFactory();
			Document result = g.getAsDocument(store);
			String fileOutput = XmlFactory.documentToString(result);
			byte[] fileOutputAsBytes = fileOutput.getBytes(HTTP.UTF_8);
			fop.write(fileOutputAsBytes);
			fop.flush();
			fop.close();
		} catch (Exception e) {
			logger.debug("Unable to write file", e);
		}
	}

}
