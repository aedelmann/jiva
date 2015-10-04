package de.aedelmann.jiva.workflow.jwl;

/**
 * @author Alexander Edelmann
 */
public interface WorkflowModel extends BaseElement {

    Start getStart();

    String getXml();
    
    Step getStep(String name);

}
