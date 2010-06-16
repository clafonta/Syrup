package com.syrup.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.syrup.model.Page;
import com.syrup.model.Project;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class PageDeleteServlet extends HttpServlet {

	/**
	 * 
	 */
	//private static Logger log = Logger.getLogger(PageDeleteServlet.class);

	private static final long serialVersionUID = 8227248014501597895L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		Project project = null;
		Page page = null;
		try {
			project = store.getProjectById(new Long(projectId));
			page = project.getPageById(new Long(pageId));
			project.deletePage(page);
			store.saveOrUpdateProject(project);
		} catch (Exception e) {
			// Do nothing
		}
		
		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();
		statusMessage.put("success", "Page deleted");
		String resultingJSON = Util.getJSON(statusMessage);
		out.println(resultingJSON);
		out.flush();
		out.close();
		return;
	}

	
}
