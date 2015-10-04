package de.aedelmann.jiva.workflow.internal.engine;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.aedelmann.jiva.workflow.engine.StepExecution;
import de.aedelmann.jiva.workflow.engine.WorkflowExecution;
import de.aedelmann.jiva.workflow.jwl.Step;

@Entity
@Table(name = "Jiva_Step")
public class StepExecutionImpl extends AbstractExecution implements StepExecution {
	
	@JoinColumn(name = "WIID")
    @ManyToOne(targetEntity=WorkflowExecutionImpl.class,cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    protected WorkflowExecution parentExecution;
	
	public StepExecutionImpl(Step model, WorkflowExecution parent) {
		this.createdOn = new Date();
		this.name = model.getName();
		this.parentExecution = parent;
	}
	
	protected StepExecutionImpl() {
		
	}

	@Override
	public WorkflowExecution getParent() {
		return parentExecution;
	}

	@Override
	public Set<String> getAvailableTransitions(Map<String, Object> variables) {
		return null;
	}

}
