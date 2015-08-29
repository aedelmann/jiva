package de.aedelmann.jiva.workflow.internal.jwl.task;


import de.aedelmann.jiva.workflow.assignment.PeopleResolutionProvider;
import de.aedelmann.jiva.workflow.jwl.Assignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "assignment")
@XmlAccessorType(XmlAccessType.FIELD)
/**
 * @author Alexander Edelmann
 */
public class JaxbAssignment implements Assignment {

    @XmlAnyElement(lax = true)
    private List<PeopleResolutionProvider> providers = new ArrayList<PeopleResolutionProvider>();

    @Override
    public List<PeopleResolutionProvider> getProviders() {
        return providers;
    }
}
