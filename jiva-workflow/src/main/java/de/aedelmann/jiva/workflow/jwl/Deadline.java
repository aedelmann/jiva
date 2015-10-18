package de.aedelmann.jiva.workflow.jwl;

import de.aedelmann.jiva.workflow.script.Script;

import java.util.Date;

/**
 * @author Alexander Edelmann
 */
public interface Deadline {

    Script<Date> getQuery();
}
