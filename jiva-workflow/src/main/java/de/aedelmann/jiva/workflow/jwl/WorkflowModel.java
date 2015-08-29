package de.aedelmann.jiva.workflow.jwl;

/**
 * @author Alexander Edelmann
 */
public interface WorkflowModel extends BaseElement, DeadlineAware {

    Start getStart();

    <NodeElement extends Node> NodeElement getNodeById(String nodeId,Class<NodeElement> expectedNode);

    String getXml();

    <RuntimeModel> RuntimeModel getRuntimeModel(Class<RuntimeModel> runtimeModelClass);
}
