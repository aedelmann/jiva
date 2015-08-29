package de.aedelmann.jiva.workflow.jwl;

import com.opensymphony.workflow.loader.WorkflowDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.MappingContext;

/**
 * @author Alexander Edelmann
 */
public interface WorkflowModel extends BaseElement, DeadlineAware {

    Start getStart();

    <NodeElement extends Node> NodeElement getNodeById(String nodeId,Class<NodeElement> expectedNode);

    String getXml();

    WorkflowDescriptor generate(MappingContext mappingContext);
}
