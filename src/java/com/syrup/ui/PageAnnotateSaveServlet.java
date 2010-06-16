package com.syrup.ui;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class PageAnnotateSaveServlet extends HttpServlet {

	private static final long serialVersionUID = 7800708128668464859L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	/*
	 * Unknown - what should GET do?
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// GET ARGUMENTS
		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		String id = req.getParameter("id");
		String top = req.getParameter("top");
		String left = req.getParameter("left");
		String height = req.getParameter("height");
		String width = req.getParameter("width");
		String text = req.getParameter("text");
		return;
	}
}
