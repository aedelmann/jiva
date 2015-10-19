package de.aedelmann.jiva.workflow.internal.engine.extensions;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowCondition;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;

/**
 * @org.apache.xbean.XBean  element="script" namespace="http://www.github.com/aedelmann/jiva/workflow/extensions/condition"
 */
public class ScriptCondition extends ScriptExecutor implements WorkflowCondition {

	@Override
	public boolean passesCondition(WorkflowContext context) {
		Object result =  this.executeScript(context);
		if (result instanceof Boolean) {
			return ((Boolean)result);
		} else {
			throw new RuntimeException("condition must return boolean result");
		}
	}
}
