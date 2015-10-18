package de.aedelmann.jiva.workflow.internal.engine.extensions;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowValidator;

/**
 * @org.apache.xbean.XBean  element="script" namespace="http://www.github.com/aedelmann/jiva/workflow/extensions/validator"
 */
public class ScriptValidator extends ScriptExecutor implements WorkflowValidator {

	@Override
	public void validate(WorkflowContext context) throws InvalidInputException {
		try {
			executeScript(context);
		} catch(Exception ex) {
			if (ex.getCause() instanceof InvalidInputException) {
				throw (InvalidInputException)ex.getCause();
			} else {
				throw new InvalidInputException(ex.getMessage());
			}
			
		}
	}
}
