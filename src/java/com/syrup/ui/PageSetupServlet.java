package com.syrup.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syrup.model.Asset;
import com.syrup.model.Page;
import com.syrup.model.Project;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class PageSetupServlet extends HttpServlet {

	private static final long serialVersionUID = 7800708128668464859L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	/*
	 * Page contains 1 or more Assets.
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		Project project = null;
		Page page = null;
		// INITIALIZE
		try {
			project = store.getProjectById(new Long(projectId));
		} catch (Exception e) {
			// Do nothing
		}
		if (project == null) {
			project = new Project();
		}
		// If project page does not exist, then default to
		// first in list
		try {
			page = project.getPageById(new Long(pageId));
		} catch (Exception e) {
			// Do nothing
		}
		if (page == null) {
			//
			if (!project.getPages().isEmpty()) {
				page = project.getPages().get(0);
			}
			// Default to first in list if not asking for new
			if ("new".equalsIgnoreCase(req.getParameter("action"))
					|| page == null) {
				page = new Page();
			}
		}
		req.setAttribute("project", project);
		req.setAttribute("pageItem", page);
		req.setAttribute("library", store.getLibraryItems());
		RequestDispatcher dispatch = req
				.getRequestDispatcher("/page_setup.jsp");

		dispatch.forward(req, resp);
	}

	/**
	 * Handles saving of page assets
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		String name = req.getParameter("name");
		String[] assetIds = req.getParameterValues("assetId[]");
		String[] sources = req.getParameterValues("source[]");
		String[] leftPositions = req.getParameterValues("left[]");
		String[] topPositions = req.getParameterValues("top[]");
		Page page = new Page();
		Project project = null;

		try {
			project = store.getProjectById(new Long(projectId));
			page.setId(new Long(pageId));

		} catch (Exception e) {
			// do nothing
		}
		page.setName(name);

		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();

		if (name == null || name.trim().length() == 0) {
			// PAGE SHOULD NOT HAVE AN EMPTY NAME
			statusMessage.put("fail", "Page not updated.");
			statusMessage.put("pageName", "Name should not be empty.");
		} else {
			// 1. UPDATE ASSET LIST
			if (assetIds != null) {

				for (String assetId : assetIds) {
					Asset assetItem = new Asset();
					String left = getValueFromArray(leftPositions, assetId);
					String top = getValueFromArray(topPositions, assetId);
					String source = getValueFromArray(sources, assetId);
					assetItem.setTop(Float.parseFloat(top));
					assetItem.setLeft(Float.parseFloat(left));
					assetItem.setSource(source);
					page.saveOrUpdateAsset(assetItem);
				}
			}
			// 2. ALL GOOD
			project.saveOrUpdatePage(page);
			store.saveOrUpdateProject(project);
			// For JSON
			statusMessage.put("success", "updated");
			statusMessage.put("pageId", "" + page.getId());
			// For JSP
			Util.saveSuccessMessage("Page updated", req);
		}

		String resultingJSON = Util.getJSON(statusMessage);
		out.println(resultingJSON);
		out.flush();
		out.close();
		return;
	}

	public static String getValueFromArray(String[] values, String id) {
		String position = null;
		for (String val : values) {
			String idDelim = id + "_";
			int i = val.indexOf(idDelim);
			if (i > -1) {
				position = val.substring(idDelim.length());
				break;
			}
		}
		return position;
	}
}
