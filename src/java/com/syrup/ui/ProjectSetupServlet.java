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

import com.syrup.model.Project;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class ProjectSetupServlet extends HttpServlet {

	private static final long serialVersionUID = -5257325297493321497L;
	private static IStorage store = StorageRegistry.SyrupStorage;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String projectId = req.getParameter("projectId");
		Project project = null;
		try {
			project = store.getProjectById(new Long(projectId));
		} catch (Exception e) {
		}
		if (project == null) {
			project = new Project();
		}
		req.setAttribute("project", project);
		req.setAttribute("projects", store.getProjects());
		RequestDispatcher dispatch = req
				.getRequestDispatcher("/project_home.jsp");

		dispatch.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// INITIALIZE
		String projectId = req.getParameter("projectId");
		String name = req.getParameter("name");
		String action = req.getParameter("action");
		Project project = null;

		try {
			project = store.getProjectById(new Long(projectId));

		} catch (Exception e) {
			// do nothing
		}
		if (project == null) {
			project = new Project();
		}
		project.setName(name);
		PrintWriter out = resp.getWriter();
		Map<String, String> statusMessage = new HashMap<String, String>();

		// ACTIONS
		// DELETE PROJECT
		if (action != null && "deleteProject".equalsIgnoreCase(action)) {
			store.deleteProject(project);
			statusMessage.put("success", "deleted project");

		} else {
			if (name == null || name.trim().length() == 0) {
				// PAGE SHOULD NOT HAVE AN EMPTY NAME
				statusMessage.put("fail", "Project not updated.");
				statusMessage.put("name", "Name should not be empty.");
			} else {

				project = store.saveOrUpdateProject(project);
				statusMessage.put("success", "updated");
				statusMessage.put("projectid", ""+ project.getId());
			}
		}

		String resultingJSON = Util.getJSON(statusMessage);
		out.println(resultingJSON);
		out.flush();
		out.close();
		return;
	}
}
