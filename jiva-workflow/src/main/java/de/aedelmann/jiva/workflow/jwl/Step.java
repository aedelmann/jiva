package de.aedelmann.jiva.workflow.jwl;


import java.util.List;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowAction;

public interface Step extends BaseElement {

    List<Transition> getTransitions();

    Transition getTransitionByName(String name);
    
    List<WorkflowAction> getPostActions();
    
    List<WorkflowAction> getPreActions();
}
