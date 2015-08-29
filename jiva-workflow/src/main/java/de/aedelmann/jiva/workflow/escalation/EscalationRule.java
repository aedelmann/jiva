package de.aedelmann.jiva.workflow.escalation;

import de.aedelmann.jiva.workflow.runtime.WorkflowInstance;

/**
 * An escalation rule is triggered when the deadline was missed.
 *
 * @author Alexander Edelmann
 */
public interface EscalationRule {

    /**
     * Executes the esclalation logic when the specified workflow instance's deadline was missed
     *
     * @param workflowInstance workflow instance whose deadline was violated, never null
     */
    void escalate(WorkflowInstance workflowInstance);
}
