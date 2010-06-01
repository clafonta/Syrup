package com.syrup.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.syrup.model.Asset;
import com.syrup.model.Page;
import com.syrup.model.Project;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class PageCopyServlet extends HttpServlet {

	private static final long serialVersionUID = 7800708128668464859L;
	private static IStorage store = StorageRegistry.SyrupStorage;
	private static Logger log = Logger.getLogger(PageDeleteServlet.class);

	/*
	 * Page contains 1 or more Assets.
	 */
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		Project project = null;
		Page page = null;
		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();
		// INITIALIZE
		try {
			project = store.getProjectById(new Long(projectId));
			page = project.getPageById(new Long(pageId));
			if (page != null) {
				Page newPage = new Page();
				newPage.setName(page.getName()+ "(copy)");
				for (Asset asset : page.getAssets()) {
					asset.setId(null);
					newPage.saveOrUpdateAsset(asset);
				}
				Page dupPage = project.saveOrUpdatePage(newPage);
				store.saveOrUpdateProject(project);
				statusMessage.put("success", "Page duplicated.");
				statusMessage.put("pageId", "" + dupPage.getId());
			}
		} catch (Exception e) {
			// Do nothing
			log.debug("Unable to duplicate page with ID '" + pageId
					+ "' and project ID '" + projectId + "'", e);
		}
		if (statusMessage.get("success") == null) {
			statusMessage.put("fail", "Unable to duplicate");
		}
		String resultingJSON = Util.getJSON(statusMessage);
		out.println(resultingJSON);
		out.flush();
		out.close();
		return;
	}
}
