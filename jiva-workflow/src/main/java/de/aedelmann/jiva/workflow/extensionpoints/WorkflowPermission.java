package de.aedelmann.jiva.workflow.extensionpoints;

public interface WorkflowPermission extends WorkflowModelExtension {

	boolean isAllowed(WorkflowContext context);
}
