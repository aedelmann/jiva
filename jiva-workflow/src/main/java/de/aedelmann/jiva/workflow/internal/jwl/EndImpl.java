package de.aedelmann.jiva.workflow.internal.jwl;

import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;

/**
 * @org.apache.xbean.XBean  element="end" namespace="http://www.github.com/aedelmann/jiva/workflow/jwl"
 */
public class EndImpl extends StepImpl implements de.aedelmann.jiva.workflow.jwl.End {
	
    @Override public void validate() throws ModelValidationProblem {
    	super.validate();
    	
        if (getTransitions().size() > 0) {
            throw new ModelValidationProblem(
                "End element must not have any outgoing transitions!");
        }
    }

}

/* EOF */
