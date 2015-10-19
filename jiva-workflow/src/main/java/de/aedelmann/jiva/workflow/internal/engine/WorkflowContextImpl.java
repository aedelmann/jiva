package de.aedelmann.jiva.workflow.internal.engine;

import de.aedelmann.jiva.workflow.engine.Execution;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;
import de.aedelmann.jiva.workflow.jwl.BaseElement;

public class WorkflowContextImpl implements WorkflowContext {

	private Execution execution; 
	private BaseElement model;
		
	public WorkflowContextImpl(Execution execution, BaseElement model) {
		this.execution = execution;
		this.model = model;
	}

	
	public Execution getExecution() {
		return execution;
	}
	
	public BaseElement getModel() {
		return model;
	}

}
