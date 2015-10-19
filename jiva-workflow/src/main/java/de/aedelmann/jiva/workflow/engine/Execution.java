package de.aedelmann.jiva.workflow.engine;

import java.util.Date;
import java.util.Map;

public interface Execution {
	
	long getId();

    String getName();

    Date getCreatedOn();
    
    Map<String,Object> getVariables();
    
    void setVariable(String key, Object value);

    WorkflowExecution getParent();
  
}
