package de.aedelmann.jiva.workflow.internal.model;


import com.opensymphony.workflow.spi.Step;
import de.aedelmann.jiva.workflow.jwl.Task;
import de.aedelmann.jiva.workflow.model.SubtaskInstance;
import de.aedelmann.jiva.workflow.model.TaskInstance;
import de.aedelmann.jiva.workflow.model.TaskState;
import de.aedelmann.jiva.workflow.model.WorkflowInstance;

import javax.persistence.CascadeType;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OSWorkflowTaskInstance implements TaskInstance {

    @OneToOne(
            cascade = { CascadeType.PERSIST, CascadeType.MERGE },
            optional = false
    )
    protected WorkflowInstance workflowInstance;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long identifier;

    protected Step step;

    @Transient
    protected Task taskModel;

    @Enumerated(EnumType.STRING)
    protected TaskState taskState;

    @Override
    public String getId() {
        return Long.toString(identifier);
    }

    public Long getIdentifier() {
        return identifier;
    }

    public OSWorkflowTaskInstance(WorkflowInstance workflowInstance) {
        this.workflowInstance = workflowInstance;
        this.taskState = TaskState.READY;
    }

    @Override
    public Date getCreatedOn() {
        return step.getStartDate();
    }

    @Override
    public Task getModel() {
        return taskModel;
    }

    @Override
    public WorkflowInstance getParent() {
        return workflowInstance;
    }

    @Override
    public List<String> getPotentialOwners() {
        return Collections.emptyList();
    }

    @Override
    public String getActualOwner() {
        return null;
    }

    @Override
    public TaskState getState() {
        return taskState;
    }

    @Override
    public List<SubtaskInstance> getSubTasks() {
        return Collections.emptyList();
    }

    public void setTaskModel(Task taskModel) {
        this.taskModel = taskModel;
    }
}
