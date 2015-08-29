package de.aedelmann.jiva.workflow.script;

/**
 *
 * @author Alexander Edelmann
 */
public interface Query<Result> {

    String getLanguage();

    String getExpression();

    Result evaluate(Object context);
}
