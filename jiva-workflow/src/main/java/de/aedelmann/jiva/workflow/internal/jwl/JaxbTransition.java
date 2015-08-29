package de.aedelmann.jiva.workflow.internal.jwl;

import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.ConditionsDescriptor;
import com.opensymphony.workflow.loader.DescriptorFactory;
import com.opensymphony.workflow.loader.RestrictionDescriptor;
import com.opensymphony.workflow.loader.ResultDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.Constants;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.MappingContext;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.OSWorkflowMapper;
import de.aedelmann.jiva.workflow.internal.jwl.mapper.OSWorkflowUtils;
import de.aedelmann.jiva.workflow.jwl.Node;
import de.aedelmann.jiva.workflow.jwl.Start;
import de.aedelmann.jiva.workflow.jwl.Task;
import de.aedelmann.jiva.workflow.jwl.Transition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "transition")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbTransition extends AbstractJaxbElement implements Transition,OSWorkflowMapper {

    @XmlIDREF
    @XmlAttribute
    private AbstractJaxbNode to = null;

    @XmlTransient
    private Node parent = null;

    @XmlAttribute
    private boolean auto = false;

    @XmlTransient
    private String oswid;

    public JaxbTransition(String id, Node to) {
        super(id);
        this.to = (AbstractJaxbNode)to;
    }

    protected JaxbTransition() {

    }

    @Override
    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = (AbstractJaxbNode)to;
    }

    @SuppressWarnings("unchecked")
    public AbstractDescriptor map(MappingContext context) {
        DescriptorFactory factory = new DescriptorFactory();
        ActionDescriptor ad = factory.createActionDescriptor();
        ad.setId(context.getActionGenerator().next());
        ad.setName(getId());
        ad.setAutoExecute(auto);

        ResultDescriptor ur = factory.createResultDescriptor();
        ur.setOldStatus("Finished");
        ur.setStatus(getTo().getId());

        if (parent instanceof Start) {
            ad.setParent(context.getWorkflowDescriptor());
        } else {
            ad.setParent(OSWorkflowUtils.findStepByName(context.getWorkflowDescriptor(),
                    parent.getId()));
        }

        if (parent instanceof Task) {
            ConditionsDescriptor andDescriptor = factory.createConditionsDescriptor();
            andDescriptor.setType(Constants.CONDITION_TYPE_AND);

            RestrictionDescriptor restrictionDescriptor = new RestrictionDescriptor();
            restrictionDescriptor.setConditionsDescriptor(andDescriptor);
            ad.setRestriction(restrictionDescriptor);
        }

        ad.setUnconditionalResult(ur);

        AbstractDescriptor abstractDescriptor = to.map(context);
        ur.setStep(abstractDescriptor.getId());

        return ad;
    }

    @Override
    public void reset() {

    }

    public boolean equals(Object o) {

        if ((o == null) || !(o instanceof Transition)) {
            return false;
        }

        Transition refTransition = (Transition) o;

        if (this.getId().equals(refTransition.getId())) {

            return true;
        }

        return false;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
