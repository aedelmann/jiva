package de.aedelmann.jiva.workflow.extensionpoints;

import java.util.Map;

import de.aedelmann.jiva.workflow.engine.Execution;
import de.aedelmann.jiva.workflow.jwl.BaseElement;

public interface WorkflowContext {

	Map<String,Object> getVariables();
	
	Execution getExecution();
	
	BaseElement getModel();
}
