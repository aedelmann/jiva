package de.aedelmann.jiva.workflow.internal.jwl;

import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.ActionDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.MappingContext;
import de.aedelmann.jiva.workflow.jwl.Node;
import de.aedelmann.jiva.workflow.jwl.Start;
import de.aedelmann.jiva.workflow.jwl.Transition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "start")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbStart extends AbstractJaxbNode implements Start {

    public JaxbStart(Node node) {
        super(node);
    }

    protected JaxbStart() {
        super("start",null);
    }

    public Transition getTransition() {
        return getTransitions().get(0);
    }

    @SuppressWarnings("unchecked")
    public AbstractDescriptor map(MappingContext context) {
        JaxbTransition t = (JaxbTransition) getTransition();
        ActionDescriptor ad = (ActionDescriptor) t.map(context);

        context.getWorkflowDescriptor().addInitialAction(ad);

        return context.getWorkflowDescriptor();
    }



    @Override
    protected String getNodeType() {
        return "start";
    }

}
