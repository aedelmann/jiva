package de.aedelmann.jiva.workflow.internal.jwl;

import de.aedelmann.jiva.workflow.jwl.ModelValidationProblem;


/**
 * @org.apache.xbean.XBean  element="parallel" namespace="http://www.github.com/aedelmann/jiva/workflow/jwl"
 */
public class Parallel extends StepImpl {

    @Override public void validate() throws ModelValidationProblem {
    	super.validate();
    	
    	if (applicationContext.getBeansOfType(Join.class).isEmpty()) {
    		throw new ModelValidationProblem("Parallel element must have a belonging join node");
    	}
    }
}

/* EOF */
