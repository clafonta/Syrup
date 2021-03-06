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

import java.util.List;

import com.syrup.model.LibraryItem;
import com.syrup.model.Project;

/**
 * How Syrup stores itself.
 * @author chad.lafontaine
 *
 */
public interface IStorage {	

	public void deleteEverything();
	public Project getProjectById(Long id);
	public Project saveOrUpdateProject(Project project);
	public void deleteProject(Project project);
	public List<Project> getProjects();

	// Library items are generated by images located 
	// on the file system. Library state is kept in
	// memory and not written to XML. Note: 
	// this means Project pages may refer to LibraryItems
	// that no longer exist, e.g. someone removed/renamed
	// an image. 
	public LibraryItem getLibraryItemById(Long id);
	public LibraryItem getLibraryItemByName(String name);
	public List<LibraryItem> getLibraryItems();
	public LibraryItem saveOrUpdateLibraryItem(LibraryItem item);
	
}
