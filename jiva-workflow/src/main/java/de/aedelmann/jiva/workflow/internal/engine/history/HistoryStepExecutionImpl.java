package de.aedelmann.jiva.workflow.internal.engine.history;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import de.aedelmann.jiva.workflow.engine.Execution;
import de.aedelmann.jiva.workflow.engine.WorkflowExecution;
import de.aedelmann.jiva.workflow.engine.history.HistoryExecution;
import de.aedelmann.jiva.workflow.internal.engine.AbstractExecution;
import de.aedelmann.jiva.workflow.internal.engine.WorkflowExecutionImpl;

@Entity
@Table(name = "Jiva_HistoryStep")
public class HistoryStepExecutionImpl extends AbstractExecution implements HistoryExecution {

	@JoinColumn(name = "WIID")
    @ManyToOne(targetEntity=WorkflowExecutionImpl.class,cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
    protected WorkflowExecution parentExecution;
	
	@Temporal(TemporalType.TIMESTAMP)
    protected Date completedOn;
	
	public HistoryStepExecutionImpl(Execution execution) {
		this.id = execution.getId();
		this.createdOn = execution.getCreatedOn();
		this.completedOn = new Date();
		this.name = execution.getName();
		this.parentExecution = execution.getParent();
	}
	
	protected HistoryStepExecutionImpl() {
		
	}

	@Override
	public WorkflowExecution getParent() {
		return parentExecution;
	}

	@Override
	public Date getCompletedOn() {
		return completedOn;
	}

}
