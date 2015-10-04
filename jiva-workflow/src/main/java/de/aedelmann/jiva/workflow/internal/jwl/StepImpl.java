package de.aedelmann.jiva.workflow.internal.jwl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowAction;
import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;
import de.aedelmann.jiva.workflow.jwl.Step;
import de.aedelmann.jiva.workflow.jwl.Transition;

/**
 * @org.apache.xbean.XBean  element="step" namespace="http://www.github.com/aedelmann/jiva/workflow/jwl"
 */
public class StepImpl extends WorkflowElement implements Step {

    protected List<Transition> transitions = new ArrayList<Transition>();
   
	private List<WorkflowAction> preActions = new ArrayList<WorkflowAction>();
    
    private List<WorkflowAction> postActions = new ArrayList<WorkflowAction>();

    public List<WorkflowAction> getPreActions() {
		return preActions;
	}

	public void setPreActions(List<WorkflowAction> preActions) {
		this.preActions = preActions;
	}

	public List<WorkflowAction> getPostActions() {
		return postActions;
	}

	public void setPostActions(List<WorkflowAction> postActions) {
		this.postActions = postActions;
	}

	/**
     * @org.apache.xbean.FlatCollection  childElement="transition"
     */
    public List<Transition> getTransitions() {
        return transitions;
    }

    public Transition getTransitionByName(String transitionId) {

        for (Transition t : getTransitions()) {

            if (t.getName().equals(transitionId)) {
                return t;
            }
        }

        return null;
    }

    public void setTransitions(List<Transition> transitions) {
        this.transitions = transitions;
    }
    
    public boolean equals(Object o) {

        if ((o == null) || !(o instanceof StepImpl)) {
            return false;
        }

        StepImpl refNode = (StepImpl) o;

        if (this.getName().equals(refNode.getName())) {

            for (Transition t : transitions) {

                if (!refNode.getTransitions().contains(t)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode() {
        return this.getName().hashCode();
    }

    public void validate() throws ModelValidationProblem {
        Set<String> transitionNames = new HashSet<String>();

        for (Transition t : getTransitions()) {
            t.validate();

            if (transitionNames.contains(t.getName())) {
                throw new ModelValidationProblem("transition with name '" + t.getName() +
                    " has already been defined!");
            }
        }
    }
    
    @PostConstruct
    public void init() {
    	for (Transition transition : this.transitions) {
    		((TransitionImpl)transition).setParent(this);
    	}
    }
 }

/* EOF */
