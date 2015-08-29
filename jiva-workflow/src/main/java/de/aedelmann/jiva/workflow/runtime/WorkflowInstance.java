package de.aedelmann.jiva.workflow.runtime;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Edelmann
 */
public interface WorkflowInstance {

    String getId();

    Date getCreatedOn();

    String getDeploymentId();

    String getInitiator();

    List<String> getPotentialOwners();

    String getActualOwner();

    Date getCompleteBy();

    WorkflowState getState();
}
