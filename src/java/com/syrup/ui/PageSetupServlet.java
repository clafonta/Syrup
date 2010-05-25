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

		String pageId = req.getParameter("pageId");
		Page pageItem = null;
		try {
			pageItem = store.getPageById(new Long(pageId));
		} catch (Exception e) {
			pageItem = new Page();
		}
		req.setAttribute("pageItem", pageItem);
		RequestDispatcher dispatch = req
				.getRequestDispatcher("/page_setup.jsp");

		dispatch.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String pageId = req.getParameter("pageId");
		String shortName = req.getParameter("shortName");
		String assetId = req.getParameter("assetId");
		String top = req.getParameter("top");
		String left = req.getParameter("left");
		String sourceValue = req.getParameter("sourceValue");
		String action = req.getParameter("action");
		Page pageItem = null;
		Asset assetItem = null;

		try {
			pageItem = store.getPageById(new Long(pageId));
		} catch (Exception e) {
			// do nothing
		}
		if (pageItem == null) {
			pageItem = new Page();
		}
		pageItem.setShortName(shortName);
		try {
			assetItem = pageItem.getAssetById(new Long(assetId));
		} catch (Exception e) {
			// do nothing
		}
		if (assetItem == null) {
			assetItem = new Asset();
		}
		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();
		// DELETE PAGE
		if (action != null && "deletePage".equalsIgnoreCase(action)) {
			store.deletePage(pageItem);
			statusMessage.put("success", "deleted");

		} else {
			// UPDATE PAGE
			if (assetId != null) {
				try {
					assetItem.setLeft(Float.parseFloat(left));
					assetItem.setTop(Float.parseFloat(top));
					assetItem.setSource(sourceValue);
				} catch (Exception e) {
					//
				}
				// DELETE ASSET?
				if (action != null && "deleteAsset".equalsIgnoreCase(action)) {
					pageItem.deleteAsset(assetItem);
				} else {
					// UPDATE ASSET
					pageItem.saveOrUpdateAsset(assetItem);
				}
			}
			store.saveOrUpdatePage(pageItem);
			statusMessage.put("success", "updated");
			statusMessage.put("pageId", "" + pageItem.getId());
		}
		
		

		String resultingJSON = Util.getJSON(statusMessage);
		out.println(resultingJSON);
		out.flush();
		out.close();
		return;
	}
}

