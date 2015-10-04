package de.aedelmann.jiva.workflow.extensionpoints;


import java.util.Set;

/**
 * Extension Point to plugin custom logic to resolve assignees for a workflow
 *
 * @author Alexander Edelmann
 */
public interface PeopleResolutionProvider extends WorkflowModelExtension {

    Set<String> resolve(WorkflowContext workflowContext);
}
