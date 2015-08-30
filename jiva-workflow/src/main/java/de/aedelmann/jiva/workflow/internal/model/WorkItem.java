package de.aedelmann.jiva.workflow.internal.model;

import de.aedelmann.jiva.workflow.model.WorkflowInstance;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name="Jiva_Workitem")
public class WorkItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected long id;

    protected boolean claimed;

    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn;

    @JoinColumn(name = "WIID")
    @ManyToOne(targetEntity=OSWorkflowInstance.class,cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    protected WorkflowInstance workflowInstance;

    protected String assignee = "";

    protected WorkItem() {
    }

    public WorkItem(WorkflowInstance workflowInstance, Date createdOn, String assignee, boolean claimed) {
        this.workflowInstance = workflowInstance;
        this.createdOn = createdOn;
        this.assignee = assignee;
        this.claimed = claimed;

    }

    public String getId() {
        return Long.toString(id);
    }

    public void setId(String id) {
        this.id = Long.parseLong(id);
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public Date getCreatedOn() {
        return this.createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public WorkflowInstance getWorkflowInstance() {
        return this.workflowInstance;
    }

    public void setWorkflowInstance(WorkflowInstance workflowInstance) {
        this.workflowInstance = workflowInstance;
    }


    public String getAssignee() {
        return this.assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkItem workItem = (WorkItem) o;

        if (claimed != workItem.claimed) return false;
        if (id != workItem.id) return false;
        if (!assignee.equals(workItem.assignee)) return false;
        if (createdOn != null ? !createdOn.equals(workItem.createdOn) : workItem.createdOn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (claimed ? 1 : 0);
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + assignee.hashCode();
        return result;
    }
}
