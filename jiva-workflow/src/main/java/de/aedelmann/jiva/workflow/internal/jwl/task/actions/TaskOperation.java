package de.aedelmann.jiva.workflow.internal.jwl.task.actions;


import com.opensymphony.workflow.loader.ActionDescriptor;
import com.opensymphony.workflow.loader.StepDescriptor;
import de.aedelmann.jiva.workflow.internal.jwl.mapping.MappingContext;

/**
 * @author Alexander Edelmann
 */
public interface TaskOperation {

    ActionDescriptor build(MappingContext context, StepDescriptor taskDescriptor);
}
