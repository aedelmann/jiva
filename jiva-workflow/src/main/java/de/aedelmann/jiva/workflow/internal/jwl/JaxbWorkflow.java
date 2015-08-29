package de.aedelmann.jiva.workflow.internal.jwl;

import com.opensymphony.workflow.loader.DescriptorFactory;
import com.opensymphony.workflow.loader.WorkflowDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.MappingContext;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.OSWorkflowMapper;
import de.aedelmann.jiva.workflow.jwl.Deadline;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

import de.aedelmann.jiva.workflow.jwl.Node;
import de.aedelmann.jiva.workflow.jwl.Start;

@XmlRootElement(name = "workflow")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbWorkflow extends AbstractJaxbElement implements WorkflowModel {

    @XmlAnyElement(lax = true)
    private List<Node> nodes = new ArrayList<Node>();

    @XmlTransient
    private String xml;

    @Override
    public Start getStart() {
        for (Node node : nodes) {
            if (node instanceof Start) {
                return (Start)node;
            }
        }
        return null;
    }

    private Start getStartNode() {
        for (Node node : nodes) {
            if (node instanceof Start) {
                return ((Start) node);
            }
        }
        return null;
    }

    @Override
    public String getXml() {
        return xml;
    }

    @Override
    public WorkflowDescriptor generate(MappingContext context) {

        WorkflowDescriptor descriptor = DescriptorFactory.getFactory().createWorkflowDescriptor();
        context.setWorkflowDescriptor(descriptor);

        Start startNode = getStartNode();

        ((OSWorkflowMapper) startNode).map(context);

        return descriptor;
    }

    @Override
    public <NodeElement extends Node> NodeElement getNodeById(String nodeId,Class<NodeElement> expectedNode) {
        for (Node node : nodes) {
            if (node.getId().equals(nodeId)) {
                return (NodeElement)node;
            }
        }

        return null;
    }

    public void validate() {
        //TODO:
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public void init() {
        for (Node node : nodes) {
            ((AbstractJaxbNode)node).init();
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public Deadline getCompletionDeadline() {
        return null;
    }
}
