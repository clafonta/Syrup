package com.syrup.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syrup.model.*;
import com.syrup.model.Project;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class AssetDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227248014501597895L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		String assetId = req.getParameter("assetId");
		Project project = null;
		Asset asset = null;
		Page page = null;
		try {
			project = store.getProjectById(new Long(projectId));
			page = project.getPageById(new Long(pageId));
			asset = page.getAssetById(new Long(assetId));
			page.deleteAsset(asset);
			project.saveOrUpdatePage(page);
			store.saveOrUpdateProject(project);
		} catch (Exception e) {
			// Do nothing
		}
		
		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();
		statusMessage.put("success", "Asset deleted");
		String resultingJSON = Util.getJSON(statusMessage);
		out.println(resultingJSON);
		out.flush();
		out.close();
		return;
	}

	
}
