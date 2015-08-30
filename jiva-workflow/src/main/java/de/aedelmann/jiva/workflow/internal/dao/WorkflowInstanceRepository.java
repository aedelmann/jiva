package de.aedelmann.jiva.workflow.internal.dao;


import de.aedelmann.jiva.workflow.internal.model.OSWorkflowInstance;
import org.springframework.data.repository.CrudRepository;

public interface WorkflowInstanceRepository extends CrudRepository<OSWorkflowInstance, Long> {
}
