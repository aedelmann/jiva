package de.aedelmann.jiva.workflow;


import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.model.Attachment;
import de.aedelmann.jiva.workflow.model.Comment;
import de.aedelmann.jiva.workflow.model.WorkflowInstance;

import java.util.List;

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
     * @pre {@link DeploymentService#getDeployment(String) != null}
     * @post workflow was created in persistent storage
     *
     * @param deploymentId
     * @throws de.aedelmann.jiva.workflow.model.WorkflowNotFoundException
     * @return newly created workflow instance.
     */
    WorkflowInstance startWorkflow(String deploymentId);

    /**
     * @pre {@link WorkflowService#startWorkflow(String)}
     * Fetches all transitions that the conditions are allowing to be taken
     * @param workflowInstanceId
     * @return a list of all transitions available for the current step
     */
    List<Transition> getAvailableTransitions(String workflowInstanceId);

    /**
     * @pre {@link WorkflowService#getAvailableTransitions(String)} contains transitionName
     * @post modified workflow instance in the new step the taken transition was pointing to
     * Takes the transition for the specified workflow instance
     * @param workflowInstanceId
     * @param transitionId
     * @return
     */
    WorkflowInstance takeTransition(String workflowInstanceId, String transitionId);

    /**
     * gets a specific workflow instance by its id
     *
     * @param workflowInstanceId
     * @return active or completed workflow instance, can be null
     */
    WorkflowInstance getWorkflow(String workflowInstanceId);

    /**
     * Adds a new comment to the workflow
     * @param workflowInstanceId
     * @param comment
     * @return the newly created comment
     */
    Comment addComment(String workflowInstanceId, String comment);

    /**
     * Adds a new reply for an existing comment
     * @param commentId id of the comment this reply is for
     * @param message actual content of the message
     * @return the newly created reply comment
     */
    Comment addCommentReply(String commentId, String message);

    /**
     * Adds a new attachment to a workflow instance
     * @param workflowInstanceId
     * @param name
     * @param contentType content specific type
     * @param data actual attachment data
     * @return the newly created attachment that was attached to the workflow instance
     */
    Attachment addAttachment(String workflowInstanceId, String name, String contentType, byte[] data);

    /**
     * Gets a list of all attachments for a workflow instance
     * @param workflowInstanceId
     * @return
     */
    public List<Attachment> getAttachments(String workflowInstanceId);

    /**
     * Gets a list of all comments for a workflow instance
     * @param workflowInstanceId
     * @return
     */
    public List<Comment> getComments(String workflowInstanceId);
}
