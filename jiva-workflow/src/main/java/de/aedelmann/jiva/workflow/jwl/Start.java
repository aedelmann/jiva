package de.aedelmann.jiva.workflow.jwl;

/**
 * @author Alexander Edelmann
 */
public interface Start extends Step {

    Transition getTransition();
}
