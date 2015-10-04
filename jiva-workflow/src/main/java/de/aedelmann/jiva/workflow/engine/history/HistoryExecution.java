package de.aedelmann.jiva.workflow.engine.history;

import java.util.Date;

import de.aedelmann.jiva.workflow.engine.Execution;

public interface HistoryExecution extends Execution {

    Date getCompletedOn();
}
