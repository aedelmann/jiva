package de.aedelmann.jiva.workflow.internal.engine;


import com.opensymphony.workflow.WorkflowContext;
import de.aedelmann.jiva.workflow.model.WorkflowEnvironment;

/**
 * @author Alexander Edelmann
 */
public class JivaWorkflowContext implements WorkflowContext {


    @Override
    public String getCaller() {
        return WorkflowEnvironment.current().getCurrentUserId();
    }

    @Override
    public void setRollbackOnly() {
    }
}
