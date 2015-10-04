package de.aedelmann.jiva.workflow.extensionpoints;

import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;

public interface WorkflowModelExtension {

	void validate() throws ModelValidationProblem;
}
