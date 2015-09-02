package de.aedelmann.jiva.workflow.internal.model;


import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import com.opensymphony.workflow.spi.Step;
import de.aedelmann.jiva.workflow.assignment.PeopleResolutionProvider;
import de.aedelmann.jiva.workflow.jwl.Task;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;
import de.aedelmann.jiva.workflow.model.TaskInstance;
import de.aedelmann.jiva.workflow.model.TaskState;
import de.aedelmann.jiva.workflow.model.WorkflowInstance;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Alexander Edelmann
 */
@Entity
@Table(name="Jiva_Workflow")
public class OSWorkflowInstance implements WorkflowInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long identifier;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date completeBy;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date completedOn;

    protected String initiator;

    @Transient
    private WorkflowModel workflowModel;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            mappedBy = "workflowInstance",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WorkItem> workItems = new ArrayList<>();

    private long workflowInstanceId;

    @Enumerated(EnumType.STRING)
    protected TaskState taskState;

    @Transient
    private Workflow workflow;

    public OSWorkflowInstance(Workflow workflow, String initiator, WorkflowModel model) {
        this.workflowModel = model;
        this.workflow = workflow;
        this.createdOn = new Date();
        this.initiator = initiator;
    }

    @Override
    public String getId() {
        return Long.toString(identifier);
    }

    public Long getIdentifier() {
        return identifier;
    }

    @Override
    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public WorkflowModel getModel() {
        return workflowModel;
    }

    @Override
    public String getInitiator() {
        return this.initiator;
    }

    @Override
    public boolean isCompleted() {
        return completedOn != null;
    }

    @Override
    public Date getCompletedOn() {
        return completedOn;
    }

    @Override
    public List<String> getPotentialOwners() {
        List<String> assigneeIds = new ArrayList<>(workItems.size());
        for (WorkItem workItem : workItems) {
            assigneeIds.add(workItem.getAssignee());
        }
        return assigneeIds;
    }

    public void resolvePotentialOwners(Step currentStep) {
        StepDescriptor sd = workflowModel.getRuntimeModel(WorkflowDescriptor.class).getStep(currentStep.getStepId());
        Task task = workflowModel.getNodeById(sd.getName(),Task.class);
        List<String> assigneeIds = new ArrayList<>();
        for (PeopleResolutionProvider provider : task.getAssignment().getProviders()) {
            assigneeIds.addAll(provider.resolve(this));
        }

        for (String assigneeId : assigneeIds) {
            workItems.add(new WorkItem(this,new Date(),assigneeId,false));
        }
    }

    @Override
    public String getActualOwner() {
        for (WorkItem workItem : workItems) {
            if (workItem.isClaimed()) {
                return workItem.getAssignee();
            }
        }
        return null;
    }

    public void setWorkflowModel(WorkflowModel workflowModel) {
        this.workflowModel = workflowModel;
    }

    public void setWorkflowInstanceId(Long workflowInstanceId) {
        this.workflowInstanceId = workflowInstanceId;
    }

    public WorkflowDescriptor getWorkflowDescriptor() {
        return workflow.getWorkflowDescriptor(workflowModel.getName());
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OSWorkflowInstance that = (OSWorkflowInstance) o;

        if (workflowInstanceId != that.workflowInstanceId) return false;
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false;
        if (!identifier.equals(that.identifier)) return false;
        if (initiator != null ? !initiator.equals(that.initiator) : that.initiator != null) return false;
        if (taskState != that.taskState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = identifier.hashCode();
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (initiator != null ? initiator.hashCode() : 0);
        result = 31 * result + (int) (workflowInstanceId ^ (workflowInstanceId >>> 32));
        result = 31 * result + (taskState != null ? taskState.hashCode() : 0);
        return result;
    }

    public Long getOSWorkflowId() {
        return workflowInstanceId;
    }

    public void claim(String userId) {
        for (WorkItem workItem : workItems) {
            if (workItem.getAssignee().equals(userId)) {
                workItem.setClaimed(true);
                setTaskState(TaskState.CLAIMED);
            }
        }
    }

    public void release() {
        for (WorkItem workItem : workItems) {
            workItem.setClaimed(false);
        }
        setTaskState(TaskState.READY);
    }
}
