package de.aedelmann.jiva.workflow.internal.engine.extensions;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowAction;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;

/**
 * @org.apache.xbean.XBean  element="script" namespace="http://www.github.com/aedelmann/jiva/workflow/extensions/action"
 */
public class ScriptAction extends ScriptExecutor implements WorkflowAction {

	@Override
	public void execute(WorkflowContext context) {
		executeScript(context);
	}
	
	

}
