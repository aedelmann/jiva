package de.aedelmann.jiva.workflow.internal.jwl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.aedelmann.jiva.workflow.jwl.BaseElement;

public abstract class WorkflowElement implements BaseElement, BeanNameAware, ApplicationContextAware {

    protected String name = "";

    protected String title = null;

    protected String description = null;

    private String workflowName = "";
    
    private String workflowTitle = "";
    
    protected ApplicationContext applicationContext;
    
    private Map<String, String> properties = new HashMap<String, String>();


    /**
     * @org.apache.xbean.Map  keyName="key" entryName="entry"
     */
    public Map<String, String> getProperties() {
        return Collections.unmodifiableMap(this.properties);
    }

    public void setProperties(Map<String, String> entries) {
        this.properties = entries;
    }
    

    public void setBeanName(String beanName) {
        this.name = beanName;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getBeanName() {
        return this.name;
    }

    public abstract void validate();

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }

    public String getTitle() {
        return this.title == null?getName():this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setApplicationContext(ApplicationContext applicationContext) {
    	this.applicationContext = applicationContext;
    }
    
    public ApplicationContext getApplicationContext() {
    	return this.applicationContext;
    }
    
    public String getWorkflowTitle() {
		return workflowTitle;
	}

	public void setWorkflowTitle(String workflowTitle) {
		this.workflowTitle = workflowTitle;
	}
        
}

/* EOF */
