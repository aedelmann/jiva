package de.aedelmann.jiva.workflow.internal.jwl;

import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;

/**
 * @org.apache.xbean.XBean  element="join" namespace="http://www.github.com/aedelmann/jiva/workflow/jwl"
 */
public class Join extends StepImpl {

    @Override public void validate() throws ModelValidationProblem {
    	if (transitions.size() != 1) {
    		throw new ModelValidationProblem("Join element must have one outgoing transition");
    	}
    	
    	transitions.get(0).validate();
    	       	
        if (applicationContext.getBeansOfType(Parallel.class).isEmpty()) {
        		throw new ModelValidationProblem("Join element must have a belonging parallel node");
        }
    }

}

/* EOF */
