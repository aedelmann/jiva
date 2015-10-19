package de.aedelmann.jiva.workflow.internal.engine;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import de.aedelmann.jiva.workflow.engine.Execution;
import de.aedelmann.jiva.workflow.engine.WorkflowExecution;
import de.aedelmann.jiva.workflow.engine.WorkflowState;
import de.aedelmann.jiva.workflow.engine.history.HistoryExecution;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowAction;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowContext;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowValidator;
import de.aedelmann.jiva.workflow.internal.engine.history.HistoryStepExecutionImpl;
import de.aedelmann.jiva.workflow.jwl.Start;
import de.aedelmann.jiva.workflow.jwl.Step;
import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

/**
 * @author Alexander Edelmann
 */
@Entity
@Table(name="Jiva_Workflow")
public class WorkflowExecutionImpl extends AbstractExecution implements WorkflowExecution {

    protected String initiator;

    @Enumerated(EnumType.STRING)
    protected WorkflowState workflowState;
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            mappedBy = "parentExecution",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = StepExecutionImpl.class
    )
    private List<StepExecutionImpl> currentSteps = new ArrayList<StepExecutionImpl>();
    
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(
            mappedBy = "parentExecution",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = HistoryStepExecutionImpl.class
    )
    private List<HistoryExecution> historySteps = new ArrayList<HistoryExecution>();
    
    @Temporal(TemporalType.TIMESTAMP)
    protected Date completedOn;

    @Transient
    private WorkflowModel workflowModel;
    
    @Transient
    private Map<String,Object> variables = new HashMap<>();
    
    public WorkflowExecutionImpl(WorkflowModel model, String initiator) {
    	this.workflowModel = model;
    	this.name = model.getName();
    	this.createdOn = new Date();
    	this.initiator = initiator;
	}
    
    public void setWorkflowModel(WorkflowModel model) {
    	this.workflowModel = model;
    }

    @Override
    public String getInitiator() {
        return this.initiator;
    }
    
    public void setInitiator(String initiator) {
    	this.initiator = initiator;
    }
     
    @Override
    public WorkflowState getState() {
        return workflowState;
    }

	@Override
	public WorkflowExecution getParent() {
		return null; // workflow execution does not have a parent at the moment
	}

	@Override
	public List<Execution> getActiveExecutions() {
		return new ArrayList<Execution>(this.currentSteps);
	}
	
	public void addCurrentStep(StepExecutionImpl stepExecution) {
		this.currentSteps.add(stepExecution);
	}

	public void setState(WorkflowState state) {
		this.workflowState = state;
	}

	@Override
	public boolean isCompleted() {
		return completedOn != null;
	}

	@Override
	public Date getCompletedOn() {
		return completedOn;
	}

	@Override
	public List<HistoryExecution> getCompletedExecutions() {
		return this.historySteps;
	}
	
	public void start(Map<String,Object> variables) {
		final Start startModel = workflowModel.getStart();
		final Transition startTransition = startModel.getTransition();
		
		WorkflowContext context = new WorkflowContextImpl(this,workflowModel);
		setVariables(variables);
		
		for (WorkflowValidator validator : startTransition.getValidators()) {
			validator.validate(context);
		}
		
		for (WorkflowAction transitionAction : startTransition.getActions()) {
			transitionAction.execute(context);
		}
		
		// create the next step execution
		Step nextStep = startTransition.getTo();
		
		StepExecutionImpl createdStep = new StepExecutionImpl(nextStep, this);
		this.currentSteps.add(createdStep);
		
		for (WorkflowAction preAction : nextStep.getPreActions()) {
			preAction.execute(new WorkflowContextImpl(createdStep,workflowModel.getStep(createdStep.getName())));
		}
	}
	
	public void takeTransition(Execution current, Transition transition, Map<String,Object> variables) {
		this.setVariables(variables);
		WorkflowContext context = new WorkflowContextImpl(current,workflowModel.getStep(current.getName()));
		
		for (WorkflowValidator validator : transition.getValidators()) {
			validator.validate(context);
		}
		
		// leaving the previous node, so execute its post actions
		for (WorkflowAction postAction : transition.getParent().getPostActions()) {
			postAction.execute(context);
		}
		
		for (WorkflowAction transitionAction : transition.getActions()) {
			transitionAction.execute(context);
		}
		
		// create the next step execution
		Step nextStep = transition.getTo();
		
		StepExecutionImpl createdStep = new StepExecutionImpl(nextStep, current.getParent());
		this.currentSteps.remove(current);
		this.historySteps.add(new HistoryStepExecutionImpl(current));
		this.currentSteps.add(createdStep);
		
		for (WorkflowAction preAction : nextStep.getPreActions()) {
			preAction.execute(new WorkflowContextImpl(createdStep,workflowModel.getStep(createdStep.getName())));
		}
		
	}

	public void complete() {
		this.workflowState = WorkflowState.ENDED;
		this.completedOn = new Date();
		for (Iterator<StepExecutionImpl> iter = this.currentSteps.iterator();iter.hasNext(); ) {
			StepExecutionImpl current = iter.next();
			this.historySteps.add(new HistoryStepExecutionImpl(current));
			iter.remove();
		}
	}

	@Override
	public Map<String, Object> getVariables() {
		return Collections.unmodifiableMap(this.variables);
	}

	@Override
	public void setVariable(String key, Object value) {
		this.variables.put(key,value);
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables.putAll(variables);	
	}

}
