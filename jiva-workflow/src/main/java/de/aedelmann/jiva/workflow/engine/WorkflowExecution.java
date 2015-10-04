package de.aedelmann.jiva.workflow.engine;

import java.util.Date;
import java.util.List;

import de.aedelmann.jiva.workflow.engine.history.HistoryExecution;

/**
 * @author Alexander Edelmann
 */
public interface WorkflowExecution extends Execution {

    List<Execution> getActiveExecutions();
    
    List<HistoryExecution> getCompletedExecutions();

    String getInitiator();
    
    WorkflowState getState();
    
    boolean isCompleted();
    
    Date getCompletedOn();
}
