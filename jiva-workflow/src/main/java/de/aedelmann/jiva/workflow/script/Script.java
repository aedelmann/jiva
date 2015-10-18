package de.aedelmann.jiva.workflow.script;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;

/**
 *
 * @author Alexander Edelmann
 */
public interface Script<Result> {

    String getLanguage();

    Result evaluate(WorkflowContext context);
}
