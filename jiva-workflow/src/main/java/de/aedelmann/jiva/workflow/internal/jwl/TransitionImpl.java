package de.aedelmann.jiva.workflow.internal.jwl;

import java.util.ArrayList;
import java.util.List;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowAction;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowCondition;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowModelExtension;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowValidator;
import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;
import de.aedelmann.jiva.workflow.jwl.Step;
import de.aedelmann.jiva.workflow.jwl.Transition;


/**
 * @org.apache.xbean.XBean  element="transition" namespace="http://www.github.com/aedelmann/jiva/workflow/jwl"
 */
public class TransitionImpl extends WorkflowElement implements Transition {

    private StepImpl destination = null;

    private Step parent = null;

    private List<WorkflowAction> actions = new ArrayList<WorkflowAction>();
    
    private List<WorkflowCondition> conditions = new ArrayList<WorkflowCondition>();

	private List<WorkflowValidator> validators = new ArrayList<WorkflowValidator>();

    private String transitionId = null;
    
    private boolean auto = false;

	@Override public void setBeanName(String beanName) {
        ;
    }

    public List<WorkflowValidator> getValidators() {
        return this.validators;
    }

    public void setValidators(List<WorkflowValidator> validators) {
        this.validators = validators;
    }

    public List<WorkflowAction> getActions() {
        return this.actions;
    }

    public void setActions(List<WorkflowAction> actions) {
        this.actions = actions;
    }
    
    public List<WorkflowCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<WorkflowCondition> conditions) {
		this.conditions = conditions;
	}


    /**
     * @org.apache.xbean.Property  alias="to"
     * @org.apache.xbean.Flat      nestedType="de.aedelmann.jiva.workflow.internal.jwl.StepImpl"
     */
    public StepImpl getTo() {
        return destination;
    }

    public void setTo(StepImpl to) {
        this.destination = to;
    }

    public de.aedelmann.jiva.workflow.jwl.Step getParent() {
        return parent;
    }

    public void setParent(Step parent) {
        this.parent = parent;
    }
   

    public boolean equals(Object o) {

        if ((o == null) || !(o instanceof TransitionImpl)) {
            return false;
        }

        TransitionImpl refTransition = (TransitionImpl) o;

        if (this.getName().equals(refTransition.getName())) {

            return true;
        }

        return false;
    }


    @Override public void validate() throws ModelValidationProblem {

        List<WorkflowModelExtension> allExtensions = new ArrayList<WorkflowModelExtension>(getActions());
        allExtensions.addAll(getValidators());
        allExtensions.addAll(getConditions());
        
        for (WorkflowModelExtension extension : allExtensions) {
            extension.validate();
        }
    }

    @Override public String getName() {
        return getTransitionId();
    }

    public String getTransitionId() {
        return transitionId;
    }

    public void setTransitionId(String transitionId) {
        this.transitionId = transitionId;
    }    
    
    public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

}

/* EOF */
