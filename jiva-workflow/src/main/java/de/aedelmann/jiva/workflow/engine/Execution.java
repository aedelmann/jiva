package de.aedelmann.jiva.workflow.engine;

import java.util.Date;

public interface Execution {
	
	public long getId();

    public String getName();

    public Date getCreatedOn();

    public WorkflowExecution getParent();
  
}
