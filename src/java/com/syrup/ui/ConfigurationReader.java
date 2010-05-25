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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;
import com.syrup.storage.xml.XmlFileConfigurationReader;

/**
 * Consumes an XML file and configures services.
 * 
 * @author Chad.Lafontaine
 * 
 */
public class ConfigurationReader {

	private static final long serialVersionUID = 2874257060865115637L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	private static Logger logger = Logger.getLogger(ConfigurationReader.class);

	/**
	 * 
	 * @param file
	 *            - xml configuration file
	 * @throws IOException
	 * @throws SAXException
	 * @throws SAXParseException
	 */
	public void inputFile(File file) throws IOException, SAXParseException,
			SAXException {
		InputStream is = new FileInputStream(file);

		long length = file.length();

		if (length > Integer.MAX_VALUE) {

			logger.error("File too large");
		} else {

			// Create the byte array to hold the data
			byte[] bytes = new byte[(int) length];
			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}

			// Close the input stream and return bytes
			is.close();

			loadConfiguration(bytes);
		}

	}

	/**
	 * 
	 * @param data
	 * @return results (conflicts and additions).
	 * @throws IOException
	 * @throws SAXException
	 * @throws SAXParseException
	 */
	public void loadConfiguration(byte[] data)
			throws IOException, SAXParseException, SAXException {
		
		 
		String strXMLDefintion = new String(data);
		XmlFileConfigurationReader msfr = new XmlFileConfigurationReader();
		IStorage mockServiceStoreTemporary = msfr
				.readDefinition(strXMLDefintion);
		
	}

	
}
