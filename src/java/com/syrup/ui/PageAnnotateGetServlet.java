package com.syrup.ui;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.syrup.model.Page;
import com.syrup.model.Project;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

public class PageAnnotateGetServlet extends HttpServlet {

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
		// Returns an array of JSON Object, each object representing 
		// an annotation. 
		
		JSONArray arrayObj=new JSONArray();
		JSONObject object = new JSONObject();
		
		try {
			object.put("left", 161);
			object.put("width", 52);
			object.put("height", 37);
			object.put("text", "This is an ajax based note.");
			object.put("id", "eeeeeeeeeeeeeeeee");
			object.put("editable", true);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		arrayObj.put(object);
		  
		
		Page page = new Page();
		Project project = null;

		try {
			project = store.getProjectById(new Long(projectId));
			page.setId(new Long(pageId));

		} catch (Exception e) {
			// do nothing
		}

		PrintWriter out = resp.getWriter();
		
		out.println(arrayObj.toString());
		out.flush();
		out.close();
		return;
	}
}
