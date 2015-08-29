package de.aedelmann.jiva.workflow.internal.jwl;

import de.aedelmann.jiva.workflow.jwl.End;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "end")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbEnd extends AbstractJaxbNode implements End {


    @Override
    public void validate() {
        super.validate();

        if (leaving.size() > 0) {
            throw new RuntimeException(
                    "Final end element must not have any outgoing transitions!");
        }
    }

    @Override
    protected String getNodeType() {
        return "end";
    }
}
