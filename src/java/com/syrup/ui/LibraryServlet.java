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
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syrup.model.LibraryItem;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class LibraryServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -805331873207119373L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		LibraryItem libraryItem = store.getLibraryItemByName(req
				.getParameter("name"));
		// Get the absolute path of the image
		ServletContext sc = getServletContext();
		// String filename = sc.getRealPath("image.gif");
		String filePath = null;
		if (libraryItem != null) {
			filePath = libraryItem.getPath();
		} else {
			filePath = sc.getRealPath("image_not_found.png");
		}

		// Get the MIME type of the image
		String mimeType = sc.getMimeType(filePath);
		if (mimeType == null) {
			sc.log("Could not get MIME type of " + filePath);
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		// Set content type
		resp.setContentType(mimeType);
		// Set content size
		File file = new File(filePath);
		resp.setContentLength((int) file.length());
		// Open the file and output streams
		FileInputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = resp.getOutputStream();
			// Copy the contents of the file to the output stream
			byte[] buf = new byte[1024];
			int count = 0;
			while ((count = in.read(buf)) >= 0) {
				out.write(buf, 0, count);
			}
			in.close();
			out.close();
			return;
		} catch (Exception e) {
			// Out, something bad.
		} finally {
			try {

			} catch (Exception e) {
				in.close();
				out.close();
			}
		}

		PrintWriter output = resp.getWriter();
		output.println("Image not found: " + req.getParameter("name"));
		output.flush();
		output.close();
		return;

	}

}
