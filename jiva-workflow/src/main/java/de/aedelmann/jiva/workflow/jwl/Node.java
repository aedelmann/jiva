package de.aedelmann.jiva.workflow.jwl;


import java.util.List;

/**
 * @author Alexander Edelmann
 */
public interface Node extends BaseElement {

    List<Transition> getTransitions();

    Transition getTransitionByName(String name);
}
