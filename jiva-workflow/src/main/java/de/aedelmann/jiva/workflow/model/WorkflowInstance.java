package de.aedelmann.jiva.workflow.model;

import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Edelmann
 */
public interface WorkflowInstance {

    String getId();

    Date getCreatedOn();

    WorkflowModel getModel();

    String getInitiator();

    boolean isCompleted();

    Date getCompletedOn();

    List<String> getPotentialOwners();

    String getActualOwner();

    TaskState getTaskState();
}
