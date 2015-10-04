package de.aedelmann.jiva.workflow.internal.engine;

import java.util.Map;

import de.aedelmann.jiva.workflow.engine.Execution;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;
import de.aedelmann.jiva.workflow.jwl.BaseElement;

public class WorkflowContextImpl implements WorkflowContext {

	private Execution execution; 
	private BaseElement model;
	private Map<String,Object> variables;
	
	
	public WorkflowContextImpl(Execution execution, BaseElement model, Map<String,Object> variables) {
		this.execution = execution;
		this.variables = variables;
		this.model = model;
	}
	
	@Override
	public Map<String, Object> getVariables() {
		return variables;
	}
	
	public Execution getExecution() {
		return execution;
	}
	
	public BaseElement getModel() {
		return model;
	}

}
