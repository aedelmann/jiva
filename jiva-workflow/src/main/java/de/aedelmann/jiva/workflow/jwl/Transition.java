package de.aedelmann.jiva.workflow.jwl;

import java.util.List;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowAction;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowValidator;

/**
 * @author Alexander Edelmann
 */
public interface Transition extends BaseElement {

    Step getTo();
    
    List<WorkflowValidator> getValidators();

	List<WorkflowAction> getActions();

	Step getParent();
}
