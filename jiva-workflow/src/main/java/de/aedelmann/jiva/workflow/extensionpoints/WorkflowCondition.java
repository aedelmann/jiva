package de.aedelmann.jiva.workflow.extensionpoints;

public interface WorkflowCondition extends WorkflowModelExtension {

	boolean passesCondition(WorkflowContext context);
}
