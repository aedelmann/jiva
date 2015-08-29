package de.aedelmann.jiva.workflow.internal.runtime;


import com.opensymphony.workflow.WorkflowContext;

/**
 * @author Alexander Edelmann
 */
public class JivaWorkflowContext implements WorkflowContext {


    @Override
    public String getCaller() {
        return "alex"; //TODO: caller should be retrieved from spring security principal or so in the application
    }

    @Override
    public void setRollbackOnly() {
    }
}
