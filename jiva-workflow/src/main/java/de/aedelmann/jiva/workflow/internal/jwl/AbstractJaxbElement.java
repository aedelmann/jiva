package de.aedelmann.jiva.workflow.internal.jwl;


import de.aedelmann.jiva.workflow.jwl.BaseElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;

/**
 * @author Alexander Edelmann
 */
public abstract class AbstractJaxbElement implements BaseElement {

    @XmlAttribute(required = true)
    @XmlID
    protected String id;

    @XmlAttribute
    protected String name;

    @XmlElement
    protected String description;

    @Override
    public String getId() {
        return this.id;
    }

    public AbstractJaxbElement(String id) {
        this.id = id;
    }

    protected AbstractJaxbElement() {

    }

    @Override
    public String getName() {
        return name == null ? id : name ;
    }

    @Override
    public String getDescription() {
        return description == null ? "" : description;
    }
}
