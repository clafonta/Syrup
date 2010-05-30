package com.syrup.ui;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

public class PageHistorySaveServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8227248014501597895L;
	private static IStorage store = StorageRegistry.SyrupStorage;
	private static final DateFormat dateFormat = new SimpleDateFormat(
	"yyyy/MM/dd hh:mm:ss a");
	public void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		String pageId = req.getParameter("pageId");
		Project project = null;
		Page page = null;
		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();
		try {
			project = store.getProjectById(new Long(projectId));
			page = project.getPageById(new Long(pageId));
			Date now = new Date();
			String version = dateFormat.format(now);
			page.setVersion(version);
			project.addPageHistory(new Long(pageId), page);
			store.saveOrUpdateProject(project);
			statusMessage.put("success", "Page history saved");
			statusMessage.put("version", version);
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
