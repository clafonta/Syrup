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
package com.syrup.storage.xml;

import com.syrup.model.*;
import com.syrup.storage.IStorage;
import com.syrup.storage.InMemoryStorage;

import org.apache.commons.digester.Digester;
import org.xml.sax.InputSource;

/**
 * This class consumes the mock service definitions file and saves it to the
 * store.
 * 
 * @author Chad.Lafontaine
 * 
 */
public class XmlFileConfigurationParser {

	private final static String ROOT = "syrup";
	private final static String PAGE = ROOT + "/page";
	private final static String ASSET = PAGE + "/asset";

	public IStorage getMockServices(InputSource inputSource)
			throws org.xml.sax.SAXParseException, java.io.IOException,
			org.xml.sax.SAXException {

		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate(ROOT, InMemoryStorage.class);
		digester.addObjectCreate(PAGE, Page.class);
		digester.addSetProperties(PAGE, "name", "pageName");
		digester.addSetNext(PAGE, "saveOrUpdatePage");

		digester.addObjectCreate(ASSET, Asset.class);
		digester.addSetProperties(ASSET, "id", "id");
		digester.addSetProperties(ASSET, "source", "source");
		digester.addSetProperties(ASSET, "left", "left");
		digester.addSetProperties(ASSET, "top", "top");
		digester.addSetNext(ASSET, "saveOrUpdateAsset");

		IStorage c = (IStorage) digester.parse(inputSource);
		return c;
	}

}