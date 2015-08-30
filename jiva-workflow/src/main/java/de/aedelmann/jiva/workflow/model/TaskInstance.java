package de.aedelmann.jiva.workflow.model;


import de.aedelmann.jiva.workflow.jwl.Task;

import java.util.Date;
import java.util.List;

public interface TaskInstance {

    String getId();

    Date getCreatedOn();

    Task getModel();

    WorkflowInstance getParent();

    List<String> getPotentialOwners();

    String getActualOwner();

    TaskState getState();

    List<SubtaskInstance> getSubTasks();
}
