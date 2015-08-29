package de.aedelmann.jiva.workflow.internal.script;

import de.aedelmann.jiva.workflow.script.Query;

import java.util.Date;

/**
 * @author Alexander Edelmann
 */
public class TimeLiteralQuery implements Query<Date> {

    private static final String LANG_KEY = "literal";

    private String expression;

    public TimeLiteralQuery(String expression) {
        this.expression = expression;
    }

    @Override
    public String getLanguage() {
        return LANG_KEY;
    }

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public Date evaluate(Object context) {
        return new Date();
    }
}
