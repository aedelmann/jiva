package de.aedelmann.jiva.workflow.internal.dao;


import org.springframework.data.repository.CrudRepository;

import de.aedelmann.jiva.workflow.internal.engine.WorkflowExecutionImpl;

public interface WorkflowInstanceRepository extends CrudRepository<WorkflowExecutionImpl, Long> {
}
