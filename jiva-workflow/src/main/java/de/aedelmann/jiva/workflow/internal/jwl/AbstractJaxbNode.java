package de.aedelmann.jiva.workflow.internal.jwl;

import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.DescriptorFactory;
import com.opensymphony.workflow.loader.StepDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.MappingContext;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.RuntimeModelMapping;
import de.aedelmann.jiva.workflow.jwl.Node;
import de.aedelmann.jiva.workflow.jwl.Transition;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Edelmann
 */
public abstract class AbstractJaxbNode extends AbstractJaxbElement implements Node, RuntimeModelMapping<AbstractDescriptor> {

    @XmlElement
    private String formKey;

    @XmlElement(name = "transition",type = JaxbTransition.class)
    protected List<Transition> leaving = new ArrayList<Transition>();

    private StepDescriptor step = null;

    @XmlTransient
    private String oswid;

    public AbstractJaxbNode(Node node){
        this.id = node.getId();
        this.name = node.getName();
        this.description = node.getDescription();
        this.formKey = node.getFormKey();
        this.leaving = node.getTransitions();
    }

    public AbstractJaxbNode(String id, String formKey) {
        super(id);
        this.formKey = formKey;
    }

    protected AbstractJaxbNode() {

    }

    @Override
    public String getFormKey() {
        return formKey;
    }

    @Override
    public List<Transition> getTransitions() {
        return leaving;
    }

    @Override
    public Transition getTransitionByName(String name) {
        return getLeavingTransitionByName(name);
    }

    public Transition getLeavingTransitionByName(String transitionId) {

        for (Transition t : leaving) {

            if (t.getId().equals(transitionId)) {
                return t;
            }
        }

        return null;
    }

    public void validate(){
        Set<String> transitionNames = new HashSet<String>();

        for (Transition t : leaving) {

            if (transitionNames.contains(t.getId())) {
                throw new RuntimeException("transition with id '" + t.getId() +
                        " is already defined!");
            }
        }
    }

    public boolean equals(Object o) {

        if ((o == null) || !(o instanceof Node)) {
            return false;
        }

        AbstractJaxbNode refNode = (AbstractJaxbNode) o;

        if (this.getId().equals(refNode.getId())) {

            for (Transition t : leaving) {

                if (!refNode.getTransitions().contains(t)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode() {
        return this.getId().hashCode();
    }

    @SuppressWarnings("unchecked")
    public AbstractDescriptor map(MappingContext context) {

        if (step != null) {
            return step;
        }

        DescriptorFactory factory = new DescriptorFactory();

        step = factory.createStepDescriptor();
        step.setId(context.getStepGenerator().next());
        step.setName(getId());

        Map<String, String> metaAttributes = new HashMap<String, String>();
        metaAttributes.put("type", getNodeType());
        step.setMetaAttributes(metaAttributes);

        step.setParent(context.getWorkflowDescriptor());
        context.getWorkflowDescriptor().addStep(step);

        for (Transition leavingTransition : getTransitions()) {
            step.getActions().add(((JaxbTransition) leavingTransition).map(context));
        }

        return step;
    }

    protected abstract String getNodeType();

    public void init() {
        for (Transition transition : leaving) {
            ((JaxbTransition)transition).setParent(this);
        }
    }
}
