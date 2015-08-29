package de.aedelmann.jiva.workflow.deployment;

import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

import java.util.Date;

/**
 * @author Alexander Edelmann
 */
public interface Deployment {

    String getId();

    Date getCreatedOn();

    String getAuthor();

    WorkflowModel getWorkflowModel();
}
