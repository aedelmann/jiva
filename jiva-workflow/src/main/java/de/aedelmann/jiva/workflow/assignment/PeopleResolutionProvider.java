package de.aedelmann.jiva.workflow.assignment;


import de.aedelmann.jiva.workflow.runtime.WorkflowInstance;

import java.util.Set;

/**
 * Extension Point to plugin custom logic to resolve assignees for a workflow
 *
 * @author Alexander Edelmann
 */
public interface PeopleResolutionProvider {

    /**
     *
     * @param workflowInstance workflow instance whose assignees are to be resolved, never null
     * @return a list of all assignee user ids. Must not be null. In case the workflow instance does not have identified any users for
     * the specific workflow instance, the workflow instance remains in state 'unassigned'
     */
    Set<String> resolve(WorkflowInstance workflowInstance);
}
