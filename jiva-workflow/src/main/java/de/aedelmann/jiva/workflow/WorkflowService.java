package de.aedelmann.jiva.workflow;


import java.util.Map;
import java.util.Set;

import de.aedelmann.jiva.workflow.engine.WorkflowExecution;

/**
 * The workflow service allows clients to start a workflow and control its flow.
 *
 * @author Alexander Edelmann
 */
public interface WorkflowService {

    /**
     * starts a new workflow for a deployed workflow model by executing the
     * firstly defined workflow step.
     *
     * @pre {@link WorkflowModelStore#getDeployment(String) != null}
     * @post workflow was created in persistent storage
     *
     * @param deploymentId
     * @throws de.aedelmann.jiva.workflow.engine.WorkflowNotFoundException
     * @return newly created workflow execution.
     */
    WorkflowExecution startWorkflow(String workflowName, Map<String,Object> variables);

    /**
     * @pre {@link WorkflowService#startWorkflow(String)}
     * Fetches all transitions that the conditions are allowing to be taken
     * @param workflowInstanceId
     * @param variables transient variables passed to the workflow 
     * @return a list of all transitions available for the current step
     */
    Set<String> getAvailableTransitions(long workflowInstanceId, Map<String,Object> variables);

    /**
     * @pre {@link WorkflowService#getAvailableTransitions(String)} contains transitionName
     * @post modified workflow instance in the new step the taken transition was pointing to
     * Takes the transition for the specified workflow instance
     * @param workflowInstanceId
     * @param transitionName
     * @param variables transient variables passed to the workflow
     * @return
     */
    WorkflowExecution takeTransition(long workflowInstanceId, String transitionName, Map<String,Object> variables);

    /**
     * gets a specific workflow instance by its id
     *
     * @param workflowInstanceId
     * @return active or completed workflow instance, can be null
     */
    WorkflowExecution getWorkflow(long workflowInstanceId);

}
