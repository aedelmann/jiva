package de.aedelmann.jiva.workflow.internal.runtime;


import com.opensymphony.workflow.Workflow;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;
import de.aedelmann.jiva.workflow.runtime.WorkflowInstance;
import de.aedelmann.jiva.workflow.runtime.WorkflowState;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Edelmann
 */
public class OSWorkflowInstance implements WorkflowInstance {

    private Workflow workflow = null;

    private WorkflowModel workflowModel;

    private long workflowInstanceId;

    public OSWorkflowInstance(long id, Workflow workflow, WorkflowModel model) {
        this.workflow = workflow;
        this.workflowModel = model;
        this.workflowInstanceId = id;
    }

    @Override
    public String getId() {
        return Long.toString(workflowInstanceId);
    }

    @Override
    public Date getCreatedOn() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDeploymentId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getInitiator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getPotentialOwners() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getActualOwner() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Date getCompleteBy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public WorkflowState getState() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
