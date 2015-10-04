package de.aedelmann.jiva.workflow.engine;

import java.util.Map;
import java.util.Set;

public interface StepExecution extends Execution {

	Set<String> getAvailableTransitions(Map<String,Object> variables);
}
