package de.aedelmann.jiva.workflow.internal.jwl;

import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;
import de.aedelmann.jiva.workflow.jwl.Start;


/**
 * @org.apache.xbean.XBean  element="start" namespace="http://www.github.com/aedelmann/jiva/workflow/jwl"
 */
public class StartImpl extends StepImpl implements Start {

    @Override public void validate() throws ModelValidationProblem {
        super.validate();

        if (transitions.size() != 1) {
            throw new ModelValidationProblem("Start element must have one outgoing transition!");
        }

        getTransition().validate();
    }

    @Override public String getName() {
        return "start";
    }

    public de.aedelmann.jiva.workflow.jwl.Transition getTransition() {
        return this.getTransitions().get(0);
    }

}

/* EOF */
