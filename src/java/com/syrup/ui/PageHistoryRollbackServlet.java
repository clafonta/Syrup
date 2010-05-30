package com.syrup.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syrup.model.Asset;
import com.syrup.model.Page;
import com.syrup.model.Project;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class PageHistoryRollbackServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227248014501597895L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		String pageVersion = req.getParameter("pageVersion");
		Project project = null;
		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();
		try {
			project = store.getProjectById(new Long(projectId));
			Page currentPage = project.getPageById(new Long(pageId));			
			Page historicalPage = project.getPageHistoryByVersion(new Long(pageId), pageVersion);
			// We replace currentPage ASSETS with historical Assets
			currentPage.deleteAssets();
			for(Asset asset: historicalPage.getAssets()){
				
				currentPage.saveOrUpdateAsset(asset);
			}
			project.saveOrUpdatePage(currentPage);
			store.saveOrUpdateProject(project);
			statusMessage.put("success", "Page rolled back");
		} catch (Exception e) {
			// Do nothing
			statusMessage.put("fail", "An error happened. " + e.getMessage() );
		}
		
		String resultingJSON = Util.getJSON(statusMessage);
		out.println(resultingJSON);
		out.flush();
		out.close();
		return;
	}

	
}
