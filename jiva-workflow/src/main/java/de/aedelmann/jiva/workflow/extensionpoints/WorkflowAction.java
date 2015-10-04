package de.aedelmann.jiva.workflow.extensionpoints;

public interface WorkflowAction extends WorkflowModelExtension {

	void execute(WorkflowContext context);
}
