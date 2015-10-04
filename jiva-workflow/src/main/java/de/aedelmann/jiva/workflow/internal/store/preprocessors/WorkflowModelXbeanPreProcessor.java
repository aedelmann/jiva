package de.aedelmann.jiva.workflow.internal.store.preprocessors;

import org.apache.commons.lang.StringUtils;
import org.apache.xbean.spring.context.SpringApplicationContext;
import org.apache.xbean.spring.context.SpringXmlPreprocessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.aedelmann.jiva.workflow.internal.store.SpringWorkflowModelStore;

public final class WorkflowModelXbeanPreProcessor implements SpringXmlPreprocessor {

	protected static Logger logger = LoggerFactory.getLogger(WorkflowModelXbeanPreProcessor.class);
			
    public void preprocess(SpringApplicationContext appCtx, XmlBeanDefinitionReader beanDefReader,
        Document doc) {
    	
    	Element configuration = doc.getDocumentElement();

        NodeList list = doc.getElementsByTagName("workflow");

        Element originalWorkflow = (Element) list.item(0);
        
        String workflowName = originalWorkflow.getAttribute("name");
        
        String workflowTitle = originalWorkflow.getAttribute("title");
                 
        SpringWorkflowModelStore.tlData.get().setName(workflowName);
        SpringWorkflowModelStore.tlData.get().setWorkflowTitle(workflowTitle);

        Element copyWorkflow = doc.createElement("workflow");
        for (int i = 0; i < originalWorkflow.getAttributes().getLength();i++) {
        	Attr attribute = (Attr)originalWorkflow.getAttributes().item(i);
        	copyWorkflow.setAttribute(attribute.getName(), attribute.getValue());        	
        }
       
        Node node = originalWorkflow.getFirstChild();

        while (node != null) {

            if (node instanceof Element) {
                Element orginalWfElement = (Element) node;
                
                try {
                    Element copyWfElement = (Element) orginalWfElement.cloneNode(true);
                    copyWfElement.setAttribute("workflowName", workflowName);
                    copyWfElement.setAttribute("workflowTitle", workflowTitle);
                    if (copyWfElement.getLocalName().toLowerCase().equals("start")) {
                    	copyWfElement.setAttribute("name", "start");
                    }
                    
                    for (int i = 0;i < copyWfElement.getChildNodes().getLength();i++) {
                    	Node childNode = copyWfElement.getChildNodes().item(i);
                    	if (childNode instanceof Element && childNode.getLocalName().equals("transition")) {
                    		Element childElement = (Element)childNode;
                    		final String transitionId = childElement.getAttribute("name");
                    		if (!StringUtils.isBlank(transitionId)) {
                    			childElement.setAttribute("transitionId", transitionId);
                    			childElement.removeAttribute("name");
                    		}
                    	}
                    }
                    
                    copyWorkflow.appendChild(copyWfElement);

                } catch (Exception e) {
                    throw new RuntimeException("Error parsing the workflow model",e);
                }
            }

            node = node.getNextSibling();
        }

        doc.removeChild(configuration);
        doc.appendChild(copyWorkflow); 
        
    }
}

/* EOF */
