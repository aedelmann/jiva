package de.aedelmann.jiva.workflow.internal.jwl.mapper;

import com.opensymphony.workflow.loader.StepDescriptor;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import de.aedelmann.jiva.workflow.jwl.BaseElement;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Alexander Edelmann
 */
public class MappingContext {

    private IdGenerator actionGenerator = null;
    private IdGenerator stepGenerator = null;
    private WorkflowDescriptor workflowDescriptor = null;
    private BaseElement parentElement = null;

    
	private Map<String, StepDescriptor> generatedSteps = new HashMap<String, StepDescriptor>();

    public MappingContext() {
        actionGenerator = new IdGenerator();
        stepGenerator = new IdGenerator();
    }


    public IdGenerator getActionGenerator() {
        return actionGenerator;
    }

    public IdGenerator getStepGenerator() {
        return stepGenerator;
    }

    public WorkflowDescriptor getWorkflowDescriptor() {
        return this.workflowDescriptor;
    }

    public BaseElement getParentElement() {
        return parentElement;
    }

    public void setParentElement(BaseElement parentElement) {
        this.parentElement = parentElement;
    }

    public void registerStep(String stepName, StepDescriptor stepDescriptor) {

        if (!isRegistered(stepName)) {
            generatedSteps.put(stepName, stepDescriptor);
        } else
            throw new RuntimeException(stepName + " is already generated/registered!");
    }

    public boolean isRegistered(String stepName) {
        return generatedSteps.containsKey(stepName);
    }

    public StepDescriptor getRegisteredStep(String stepName) {
        return generatedSteps.get(stepName);
    }
    
    public void setWorkflowDescriptor(WorkflowDescriptor descriptor) {
    	this.workflowDescriptor = descriptor;
    }
    
}

/* EOF */
