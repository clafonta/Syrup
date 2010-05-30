package com.syrup.ui.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.syrup.model.LibraryItem;
import com.syrup.storage.IStorage;
import com.syrup.storage.StorageRegistry;

/**
 * build the result URL
 * 
 * @author chad.lafontaine
 */
public class LibraryTag extends TagSupport {

	/**
     * 
     */
	private static final long serialVersionUID = 702927192030153426L;
	private String name;
	private static IStorage store = StorageRegistry.SyrupStorage;

	@Override
	public int doStartTag() throws JspException {

		LibraryItem li = store.getLibraryItemByName(name);
		JspWriter out = pageContext.getOut();
		StringBuffer outputText = new StringBuffer();
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();
		String context = request.getContextPath();
		// Context could be:
		// '/' or '/something'
		// Name could be:
		// 'somename' or '/somename'
		outputText.append(context);
		if(!outputText.toString().endsWith("/")){
		 outputText.append("/");	
		}
		
		if (li != null && !li.isRelative()) {
			// We build a url to the LibraryServlet
			outputText.append("library?name=");
			outputText.append(name);
		} else {
			// We don't have the item in the library.
			// We'll try to reference it as a relative
			// image. 
			outputText.append("images/sample/");
			outputText.append(name);
		}
		try {
			out.print(outputText);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
