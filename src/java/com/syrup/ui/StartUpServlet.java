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
package com.syrup.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.syrup.model.LibraryItem;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class StartUpServlet extends HttpServlet {

	private static final long serialVersionUID = -6466436642921760561L;
	private static Logger logger = Logger.getLogger(StartUpServlet.class);
	private static Properties appProps = new Properties();
	public static final String APP_DEFINITIONS = "syrup_definitions.xml";
	public static final String APP_LIBRARY = "syrup_library";
	public static final String APP_LIBRARY_MISC = "misc";
	private static IStorage store = StorageRegistry.SyrupStorage;
	
	public void init() throws ServletException {

		String log4jFile = getInitParameter("log4j.properties");
		String appPropFile = getInitParameter("default.properties");
		// base directory of servlet context
		String contextPath = getServletContext().getRealPath(
				System.getProperty("file.separator"));
		contextPath = "/";

		try {
			InputStream log4jInputStream = getServletContext()
					.getResourceAsStream(contextPath + log4jFile);
			Properties log4JProperties = new Properties();
			log4JProperties.load(log4jInputStream);
			PropertyConfigurator.configure(log4JProperties);

		} catch (Exception npe) {
			System.out
					.println("Unable to find log4j.properties in servlet context");
		}

		try {
			logger.info("default.properties: "
					+ getServletContext().getResource("/web.xml"));
			InputStream appInputStream = getServletContext()
					.getResourceAsStream(contextPath + appPropFile);
			if (appInputStream == null) {
				// try classpath
				appInputStream = getClass().getResourceAsStream(
						contextPath + appPropFile);
			}
			appProps.load(appInputStream);

			// LOAD UP SAVED PROJECTS
			File f = new File(APP_DEFINITIONS);
			if (f.exists()) {
				// Slurp it up and initialize definitions.
				FileInputStream fstream = new FileInputStream(f);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fstream, Charset.forName(HTTP.UTF_8)));
				StringBuffer inputString = new StringBuffer();
				// Read File Line By Line
				String strLine = null;
				while ((strLine = br.readLine()) != null) {
					// Print the content on the console
					inputString
							.append(new String(strLine.getBytes(HTTP.UTF_8)));
				}
				ConfigurationReader reader = new ConfigurationReader();
				reader.loadConfiguration(inputString.toString().getBytes(
						HTTP.UTF_8));
			}
			
			// LOAD UP LIBRARY ITEMS
			File libraryDirectory = new File(APP_LIBRARY);
			if(libraryDirectory.exists() && libraryDirectory.isDirectory()){
				for(File file: libraryDirectory.listFiles()){
					if(file.isDirectory()){
						// Directory is a Group. 
						String groupName = file.getName();
						for(File groupItem: file.listFiles()){
							if(!groupItem.isDirectory()){
								LibraryItem li = new LibraryItem();
								li.setGroupName(groupName);
								li.setPath(groupItem.getPath());
								li.setName(groupItem.getName());
								store.saveOrUpdateLibraryItem(li);
							}
							
						}
					}else {
						LibraryItem li = new LibraryItem();
						li.setGroupName(APP_LIBRARY_MISC);
						li.setPath(file.getPath());
						li.setName(file.getName());
						store.saveOrUpdateLibraryItem(li);
					}
				}
			}
			

		}

		catch (Exception e) {
			logger.error("StartUpServlet:init()", e);
		}
	}
}
