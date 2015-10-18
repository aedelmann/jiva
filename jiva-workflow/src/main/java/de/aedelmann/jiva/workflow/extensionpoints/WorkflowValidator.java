package de.aedelmann.jiva.workflow.extensionpoints;

public interface WorkflowValidator extends WorkflowModelExtension {

	void validate(WorkflowContext context) throws InvalidInputException;
	
	public class InvalidInputException extends RuntimeException {
	
		public InvalidInputException(String msg) {
			super(msg);
		}
	}
}
