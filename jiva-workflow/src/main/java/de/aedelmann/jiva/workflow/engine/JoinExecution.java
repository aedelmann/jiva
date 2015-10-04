package de.aedelmann.jiva.workflow.engine;

import java.util.List;

public interface JoinExecution extends Execution {
	
    public List<Execution> getExecutions();
}
