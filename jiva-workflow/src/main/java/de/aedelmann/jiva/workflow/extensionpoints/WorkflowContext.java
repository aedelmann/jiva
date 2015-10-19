package de.aedelmann.jiva.workflow.extensionpoints;

import de.aedelmann.jiva.workflow.engine.Execution;
import de.aedelmann.jiva.workflow.jwl.BaseElement;

public interface WorkflowContext {
	
	Execution getExecution();
	
	BaseElement getModel();
}
