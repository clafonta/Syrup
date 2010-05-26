package com.syrup.storage.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.syrup.model.Asset;
import com.syrup.model.Page;
import com.syrup.model.Project;
import com.syrup.storage.IStorage;

/**
 * Builds DOM representing configurations. 
 * @author chad.lafontaine
 *
 */
public class XmlFileConfigurationGenerator extends XmlGeneratorSupport {
	/** Basic logger */
	

	/**
	 * Returns an element representing a mock service definitions file in XML. 
	 * 
	 * @param document
	 *            parent DOM object of this element.
	 * @param cxmlObject
	 *            value object used to build element.
	 * @return Returns an element representing a cXML root element; if request
	 *         <code>null</code>, then empty element is returned e.g.
	 *         &lt;cXML/&gt;
	 */
	public Element getElement(Document document, IStorage store) {
		
		Element rootElement = document.createElement("syrup");
				
		this.setAttribute(rootElement, "xml:lang", "en-US");
		this.setAttribute(rootElement, "version", "1.0");
		
		for(Project project: store.getProjects()){
			Element projectElement = document.createElement("project");
			rootElement.appendChild(projectElement);

			if (project != null) {
				
				// *************************************
				// We do NOT want to write out ID.
				// If we did, then someone uploading this xml definition may overwrite 
				// defined with the same ID.
				// *************************************
				projectElement.setAttribute("name", project.getName());
				
				for(Page page: project.getPages()){
					Element pageElement = document.createElement("page");
					pageElement.setAttribute("id", ""+page.getId());
					pageElement.setAttribute("name", ""+page.getName());
					for(Asset asset: page.getAssets()){
						Element assetElement = document.createElement("asset");
						assetElement.setAttribute("id", asset.getId().toString());
						assetElement.setAttribute("source",asset.getSource());
						assetElement.setAttribute("top",""+asset.getTop());
						assetElement.setAttribute("left",""+asset.getLeft());
						pageElement.appendChild(assetElement);
					}
					projectElement.appendChild(pageElement);
				}
			}
		}
		return rootElement;
	}
}
