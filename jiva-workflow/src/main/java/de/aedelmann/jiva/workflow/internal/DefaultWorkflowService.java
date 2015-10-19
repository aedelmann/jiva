package de.aedelmann.jiva.workflow.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.aedelmann.jiva.workflow.WorkflowModelStore;
import de.aedelmann.jiva.workflow.WorkflowService;
import de.aedelmann.jiva.workflow.engine.Execution;
import de.aedelmann.jiva.workflow.engine.WorkflowEnvironment;
import de.aedelmann.jiva.workflow.engine.WorkflowExecution;
import de.aedelmann.jiva.workflow.engine.WorkflowState;
import de.aedelmann.jiva.workflow.extensionpoints.WorkflowCondition;
import de.aedelmann.jiva.workflow.internal.dao.WorkflowInstanceRepository;
import de.aedelmann.jiva.workflow.internal.engine.StepExecutionImpl;
import de.aedelmann.jiva.workflow.internal.engine.WorkflowContextImpl;
import de.aedelmann.jiva.workflow.internal.engine.WorkflowExecutionImpl;
import de.aedelmann.jiva.workflow.jwl.End;
import de.aedelmann.jiva.workflow.jwl.Step;
import de.aedelmann.jiva.workflow.jwl.Transition;
import de.aedelmann.jiva.workflow.jwl.WorkflowModel;

@Service
/**
 * @author Alexander Edelmann
 */
@Transactional
public class DefaultWorkflowService implements WorkflowService {

    @Autowired
    private WorkflowModelStore modelStore;

    @Autowired
    private WorkflowInstanceRepository dao;

	@Override
	public WorkflowExecution startWorkflow(String workflowName, Map<String,Object> variables) {
		WorkflowModel model = modelStore.getModel(workflowName);
		if (model == null) {
			throw new RuntimeException("Cannot start workflow because no workflow has been found with the given name");
		}
		WorkflowExecutionImpl workflowInstance = new WorkflowExecutionImpl(model,WorkflowEnvironment.current().getCurrentUserId());
		
		workflowInstance.start(variables);
		
		workflowInstance.setState(WorkflowState.ACTIVE);
		
		dao.save(workflowInstance);
		
		return workflowInstance;
	}

	@Override
	public Set<String> getAvailableTransitions(long workflowInstanceId, Map<String, Object> variables) {
		Set<String> availableTransitions = new HashSet<>();
		WorkflowExecutionImpl workflowExecution = dao.findOne(workflowInstanceId);
		workflowExecution.setVariables(variables);
		WorkflowModel workflowModel = this.modelStore.getModel(workflowExecution.getName());
		for (Execution currentExecution : workflowExecution.getActiveExecutions()) {
			StepExecutionImpl stepExecution = (StepExecutionImpl)currentExecution;
			Step stepModel = workflowModel.getStep(stepExecution.getName());
			for (Transition transition : stepModel.getTransitions()) {
				availableTransitions.add(transition.getName());
				if (!isTransitionAvailable(stepExecution, stepModel, transition)) {
					availableTransitions.remove(transition.getName());
				}
			}
		}
		return availableTransitions;
	}
	
	@Override
	public Set<String> getAvailableTransitions(long workflowInstanceId) {
		return getAvailableTransitions(workflowInstanceId,new HashMap<String,Object>());
	}
	
	private boolean isTransitionAvailable(Execution execution, Step model, Transition transition) {
		for (WorkflowCondition condition : transition.getConditions()) {
			boolean passes = condition.passesCondition(new WorkflowContextImpl(execution,model));
			if (!passes) {
				return false;
			}
		}
		
		return true;
	}

	@Override
	public WorkflowExecution takeTransition(long workflowInstanceId, String transitionName,
			Map<String, Object> variables) {
		WorkflowExecutionImpl workflowInstance =  (WorkflowExecutionImpl)getWorkflow(workflowInstanceId);
		
		WorkflowModel workflowModel = modelStore.getModel(workflowInstance.getName());
		
		for (Execution currentExecution : workflowInstance.getActiveExecutions()) {
			final Step stepModel = workflowModel.getStep(currentExecution.getName());
			Transition transitionToTake = stepModel.getTransitionByName(transitionName);
			if (transitionToTake != null && isTransitionAvailable(currentExecution, stepModel, transitionToTake)) {
				workflowInstance.takeTransition(currentExecution, transitionToTake, variables);
				if (isWorkflowFinished(workflowInstance, transitionToTake)) {
					workflowInstance.complete();
				}
				break;
			}
		}
		
		dao.save(workflowInstance);
		return workflowInstance;
	}
	
	private boolean isWorkflowFinished(WorkflowExecution workflowInstance, Transition takenTransition) {
		return takenTransition.getTo() instanceof End;
	}

	@Override
	public WorkflowExecution getWorkflow(long workflowInstanceId) {
		WorkflowExecutionImpl workflowInstance =  dao.findOne(workflowInstanceId);
		workflowInstance.setWorkflowModel(modelStore.getModel(workflowInstance.getName()));
		return workflowInstance;
	}

}
