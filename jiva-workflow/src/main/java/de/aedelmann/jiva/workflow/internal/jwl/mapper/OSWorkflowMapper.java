package de.aedelmann.jiva.workflow.internal.jwl.mapper;

import com.opensymphony.workflow.loader.AbstractDescriptor;

/**
 * @author Alexander Edelmann
 */
public interface OSWorkflowMapper {

    public AbstractDescriptor map(MappingContext context);

    public void reset();
}
