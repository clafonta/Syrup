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

import java.io.IOException;
import java.io.PrintStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;
import com.syrup.storage.xml.XmlFactory;

/**
 * Export service definitions to XML.
 * 
 * @author Chad.Lafontaine
 * 
 */
public class ExportConfigurationServlet extends HttpServlet {
	
	private static IStorage store = StorageRegistry.SyrupStorage;

    private static final long serialVersionUID = -8618555367432628615L;

    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		XmlFactory g = new XmlFactory();
		Document result = g.getAsDocument(store);

        String fileOutput;
        try {
            fileOutput = XmlFactory.documentToString(result);
        } catch (TransformerException e) {
            throw new ServletException(e);
        }

        resp.setContentType("text/xml");
        resp.setHeader("Content-disposition", "attachment; filename=mockservice.xml");
        resp.setContentLength(fileOutput.getBytes().length);

        PrintStream out = new PrintStream(resp.getOutputStream());
        out.println(fileOutput);
    }
}
