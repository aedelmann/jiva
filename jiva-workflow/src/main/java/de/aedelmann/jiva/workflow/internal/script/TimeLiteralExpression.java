package de.aedelmann.jiva.workflow.internal.script;

import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;
import de.aedelmann.jiva.workflow.script.Script;

import java.util.Date;

/**
 * @author Alexander Edelmann
 */
public class TimeLiteralExpression implements Script<Date> {

    private static final String LANG_KEY = "literal";

    public TimeLiteralExpression(String value) {
    }

    @Override
    public String getLanguage() {
        return LANG_KEY;
    }

    @Override
    public Date evaluate(WorkflowContext context) {
        return new Date();
    }
}
