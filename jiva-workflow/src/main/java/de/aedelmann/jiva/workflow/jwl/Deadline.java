package de.aedelmann.jiva.workflow.jwl;

import de.aedelmann.jiva.workflow.script.Query;

import java.util.Date;

/**
 * @author Alexander Edelmann
 */
public interface Deadline {

    Query<Date> getQuery();
}
