package de.aedelmann.jiva.workflow.internal.jwl.mapping;

/**
 * @author Alexander Edelmann
 */
public interface RuntimeModelMapping<RuntimeModel> {

    public <RuntimeModel> RuntimeModel map(MappingContext context);
}
